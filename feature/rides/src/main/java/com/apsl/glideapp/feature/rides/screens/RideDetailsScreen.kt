package com.apsl.glideapp.feature.rides.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.UpdateNavigationBarColor
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.Clock
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toPx
import com.apsl.glideapp.feature.rides.models.RideDetailsUiModel
import com.apsl.glideapp.feature.rides.viewmodels.RideDetailsUiState
import com.apsl.glideapp.feature.rides.viewmodels.RideDetailsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
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
                CameraPositionState(
                    position = CameraPosition.fromLatLngZoom(uiState.mapCameraBounds.center, 13f)
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
                        title = { Text(text = "Ride details") },
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
                    uiState.ride?.let {
                        RideDetailsSheetContent(ride = it)
                    }
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
                            mapToolbarEnabled = true,
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
                                startCap = RoundCap(), //CustomCap()
                                endCap = RoundCap(), //CustomCap()
                                width = 2.dp.toPx()
                            )
                        }
                    }

                    Spacer(Modifier.height(sheetPeekHeight - sheetHandlePadding))
                }
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
            RideDetailsSheetTitle(imageVector = GlideIcons.Route, text = "Route")
            Spacer(Modifier.height(16.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                TitleValueText(title = "Total distance", value = "${ride.distance} m")
                Spacer(Modifier.height(4.dp))
                TitleValueText(title = "Average speed", value = ride.averageSpeed)
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "${ride.startAddress ?: "Address not defined"} - ${ride.finishAddress ?: "Address not defined "}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        Column {
            RideDetailsSheetTitle(imageVector = GlideIcons.Clock, text = "Time")
            Spacer(Modifier.height(16.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Ride lasted ${ride.timeInMinutes} minutes",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Date and time",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = ride.startDate,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${ride.startTime} - ${ride.finishTime}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RideDetailsSheetTitle(imageVector: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun RideDetailsSheetContentPreview() {
    GlideAppTheme {
        RideDetailsSheetContent(
            ride = RideDetailsUiModel(
                startAddress = "ul. Konarskiego 5A, Warszawa",
                finishAddress = "ul. Wolska 125/1, Warszawa",
                startDate = "Tuesday, May 12",
                startTime = "16:24",
                finishTime = "16:56",
                route = emptyList(),
                distance = 4154,
                averageSpeed = "15,3 km/h",
                timeInMinutes = 32
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RideDetailsScreenPreview() {
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
                    distance = 4154,
                    averageSpeed = "15.3 km/h",
                    timeInMinutes = 32
                )
            ),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {}
        )
    }
}
