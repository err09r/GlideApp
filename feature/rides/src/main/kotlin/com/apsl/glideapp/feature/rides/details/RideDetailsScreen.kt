package com.apsl.glideapp.feature.rides.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.UpdateNavigationBarColor
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.Clock
import com.apsl.glideapp.core.ui.icons.Flag
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toPx
import com.apsl.glideapp.core.util.maps.MapsUtils.calculateZoomLevel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberMarkerState
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun RideDetailsScreen(
    rideId: String,
    viewModel: RideDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getRideById(rideId)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RideDetailsScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onNavigateBack
    )
}

@Composable
fun RideDetailsScreenContent(
    uiState: RideDetailsUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit
) {
    when {
        uiState.isLoading -> LoadingScreen()
        else -> {
            val context = LocalContext.current
            val sheetPeekHeight = 272.dp
            val sheetElevation = 4.dp
            val sheetHandlePadding = 32.dp

            val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(sheetElevation)

            UpdateNavigationBarColor(color = containerColor)

            val cameraPositionState = remember(uiState.mapCameraBounds) {
                val zoom = calculateZoomLevel(uiState.mapCameraBounds)
                CameraPositionState(
                    position = CameraPosition.fromLatLngZoom(uiState.mapCameraBounds.center, zoom)
                )
            }

            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.PartiallyExpanded,
                    skipHiddenState = true
                ),
                snackbarHostState = snackbarHostState
            )

            BottomSheetScaffold(
                modifier = Modifier.fillMaxSize(),
                sheetPeekHeight = sheetPeekHeight,
                sheetShadowElevation = sheetElevation,
                sheetTonalElevation = sheetElevation,
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(CoreR.string.ride_details_screen_title)) },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(imageVector = GlideIcons.ArrowBack, contentDescription = null)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = containerColor,
                            scrolledContainerColor = containerColor,
                            navigationIconContentColor = contentColorFor(containerColor),
                            titleContentColor = contentColorFor(containerColor)
                        )
                    )
                },
                sheetContent = {
                    uiState.ride?.let { RideDetailsSheetContent(ride = it) }
                }
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    val mapProperties = remember(uiState.mapCameraBounds) {
                        MapProperties(
                            latLngBoundsForCameraTarget = uiState.mapCameraBounds,
                            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                                context,
                                CoreR.raw.map_style
                            ),
                            maxZoomPreference = 20f,
                            minZoomPreference = 11f
                        )
                    }

                    val mapUiSettings = remember {
                        MapUiSettings(
                            compassEnabled = true,
                            indoorLevelPickerEnabled = false,
                            mapToolbarEnabled = false,
                            myLocationButtonEnabled = false,
                            rotationGesturesEnabled = true,
                            scrollGesturesEnabled = true,
                            scrollGesturesEnabledDuringRotateOrZoom = false,
                            tiltGesturesEnabled = true,
                            zoomControlsEnabled = false,
                            zoomGesturesEnabled = true
                        )
                    }

                    GoogleMap(
                        modifier = Modifier.weight(1f),
                        cameraPositionState = cameraPositionState,
                        properties = mapProperties,
                        uiSettings = mapUiSettings,
                        contentPadding = PaddingValues(bottom = sheetHandlePadding)
                    ) {
                        if (uiState.ride != null) {
                            Polyline(
                                points = uiState.ride.route,
                                color = MaterialTheme.colorScheme.primary,
                                jointType = JointType.ROUND,
                                startCap = RoundCap(),
                                endCap = RoundCap(),
                                width = 4.dp.toPx()
                            )

                            StartPointMarker(
                                markerState = rememberMarkerState(
                                    key = "Start",
                                    position = uiState.ride.route.first()
                                )
                            )

                            FinishPointMarker(
                                markerState = rememberMarkerState(
                                    key = "Finish",
                                    position = uiState.ride.route.last()
                                ),
                                imageVector = GlideIcons.Flag
                            )
                        }
                    }

                    Spacer(Modifier.height(sheetPeekHeight - sheetHandlePadding))
                }
            }
        }
    }
}

@GoogleMapComposable
@Composable
fun StartPointMarker(
    modifier: Modifier = Modifier,
    markerState: MarkerState = rememberMarkerState()
) {
    MarkerComposable(
        state = markerState,
        anchor = Offset(0.5f, 0.5f),
        zIndex = 1f,
        onClick = { true }
    ) {
        Box(
            modifier = modifier
                .size(8.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
    }
}

@GoogleMapComposable
@Composable
fun FinishPointMarker(
    modifier: Modifier = Modifier,
    markerState: MarkerState = rememberMarkerState(),
    imageVector: ImageVector
) {
    MarkerComposable(state = markerState, anchor = Offset(0.5f, 0.5f), zIndex = 1f) {
        val outlineColor: Color = MaterialTheme.colorScheme.primary
        val outlineWidth: Dp = 1.dp
        val elevation: Dp = 4.dp

        Surface(
            modifier = modifier,
            shape = CircleShape,
            shadowElevation = elevation,
            tonalElevation = elevation,
            border = BorderStroke(width = outlineWidth, color = outlineColor)
        ) {
            Box(modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = outlineColor
                )
            }
        }
    }
}

@Composable
fun RideDetailsSheetContent(ride: RideDetailsUiModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            RideDetailsSheetTitle(
                imageVector = GlideIcons.Route,
                text = stringResource(CoreR.string.ride_details_sheet_title1)
            )
            Spacer(Modifier.height(8.dp))
            Column {
                TitleValueText(
                    title = stringResource(CoreR.string.ride_details_sheet_subtitle1),
                    value = stringResource(CoreR.string.value_meters, ride.distanceMeters)
                )
                Spacer(Modifier.height(4.dp))
                TitleValueText(
                    title = stringResource(CoreR.string.ride_details_sheet_subtitle2),
                    value = stringResource(CoreR.string.value_kmh, ride.averageSpeedKmh)
                )
                Spacer(Modifier.height(24.dp))

                val addressText = if (ride.startAddress == null || ride.finishAddress == null) {
                    stringResource(CoreR.string.address_not_defined)
                } else {
                    stringResource(CoreR.string.value_range, ride.startAddress, ride.finishAddress)
                }
                Text(
                    text = addressText,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        Column {
            RideDetailsSheetTitle(
                imageVector = GlideIcons.Clock,
                text = stringResource(CoreR.string.ride_details_sheet_title2)
            )
            Spacer(Modifier.height(8.dp))
            Column {
                Text(
                    text = stringResource(
                        CoreR.string.ride_details_sheet_ride_lasted,
                        ride.timeInMinutes
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(CoreR.string.ride_details_sheet_date_time),
                        modifier = Modifier.alignByBaseline(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Column(
                        modifier = Modifier.alignByBaseline(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = ride.startDate,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = stringResource(
                                CoreR.string.value_range,
                                ride.startTime,
                                ride.finishTime
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun TitleValueText(title: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            modifier = Modifier.alignByBaseline(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            modifier = Modifier.alignByBaseline(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun RideDetailsSheetTitle(imageVector: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun RideDetailsSheetContentPreview() {
    GlideAppTheme {
        RideDetailsSheetContent(
            ride = RideDetailsUiModel(
                startAddress = "ul. Konarskiego 5A, Warszawa",
                finishAddress = "ul. Wolska 125/1, Warszawa",
                startDate = "Tuesday, May 12",
                startTime = "16:24",
                finishTime = "16:56",
                route = emptyList(),
                distanceMeters = "4154",
                averageSpeedKmh = "15,3",
                timeInMinutes = "32"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RideDetailsScreenPreview() {
    GlideAppTheme {
        RideDetailsScreenContent(
            uiState = RideDetailsUiState(
                ride = RideDetailsUiModel(
                    startAddress = "ul. Konarskiego 5A",
                    finishAddress = "ul. Wolska 75",
                    startDate = "Tuesday, May 12",
                    startTime = "16:24",
                    finishTime = "16:56",
                    route = emptyList(),
                    distanceMeters = "4154",
                    averageSpeedKmh = "15.3",
                    timeInMinutes = "32"
                )
            ),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {}
        )
    }
}
