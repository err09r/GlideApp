package com.apsl.glideapp.feature.home.screens

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.RequestPermission
import com.apsl.glideapp.core.ui.getOffset
import com.apsl.glideapp.core.ui.rememberRequestPermissionState
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.home.components.DrawerContent
import com.apsl.glideapp.feature.home.components.SheetContent
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.apsl.glideapp.feature.home.maps.toLatLng
import com.apsl.glideapp.feature.home.rideservice.RideService
import com.apsl.glideapp.feature.home.viewmodels.HomeAction
import com.apsl.glideapp.feature.home.viewmodels.HomeUiState
import com.apsl.glideapp.feature.home.viewmodels.HomeViewModel
import com.apsl.glideapp.feature.home.viewmodels.UserAuthState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToAllRides: () -> Unit,
    onNavigateToWallet: () -> Unit,
    onNavigateToLocationPermission: () -> Unit,
    onNavigateToLocationRationale: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {

                is HomeAction.RideStarted -> {
                    val intent = Intent(context, RideService::class.java).apply {
                        this.action = RideService.ACTION_START
                        putExtra(RideService.RIDE_ID, action.rideId)
                    }
                    context.startForegroundService(intent)
                }

                is HomeAction.RideFinished -> {
                    val intent = Intent(context, RideService::class.java).apply {
                        this.action = RideService.ACTION_STOP
                    }
                    context.startForegroundService(intent)
                }

                is HomeAction.Toast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.userAuthState) {
        when (uiState.userAuthState) {
            UserAuthState.NotAuthenticated -> onNavigateToLogin()
            else -> Unit
        }
    }

    if (uiState.userAuthState == UserAuthState.Authenticated) {
        ComposableLifecycle { event ->
            with(viewModel) {
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        startReceivingRideEvents()
                        startReceivingLocationUpdates()
                        startObservingMapState()
                        startObservingUserLocation()
                    }

                    Lifecycle.Event.ON_RESUME -> {

                    }

                    Lifecycle.Event.ON_STOP -> {
                        stopObservingMapState()
                        stopObservingUserLocation()
                        stopReceivingLocationUpdates()
                    }

                    else -> Unit
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopReceivingLocationUpdates()
            }
        }

        HomeScreenContent(
            uiState = uiState,
            onLocationButtonClick = {},
            onOpenLocationPermissionDialog = onNavigateToLocationPermission,
            onOpenLocationRationaleDialog = onNavigateToLocationRationale,
            onVehicleSelect = viewModel::updateSelectedVehicle,
            onLoadMapDataWithinBounds = viewModel::loadMapDataWithinBounds,
            onStartRideClick = viewModel::startRide,
            onFinishRideClick = viewModel::finishRide,
            onMyRidesClick = onNavigateToAllRides,
            onWalletClick = onNavigateToWallet
        )
    }
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onLocationButtonClick: () -> Unit,
    onOpenLocationPermissionDialog: () -> Unit,
    onOpenLocationRationaleDialog: () -> Unit,
    onVehicleSelect: (VehicleClusterItem?) -> Unit,
    onLoadMapDataWithinBounds: (LatLngBounds) -> Unit,
    onStartRideClick: () -> Unit,
    onFinishRideClick: () -> Unit,
    onMyRidesClick: () -> Unit,
    onWalletClick: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val statusBarHeightPx = WindowInsets.statusBars.getTop(density)
    val navigationBarHeightPx = WindowInsets.navigationBars.getBottom(density)

    val bottomSheetOffsetPx = scaffoldState.bottomSheetState.getOffset()
    val bottomSheetOffset = with(density) {
        (bottomSheetOffsetPx - statusBarHeightPx).toDp()
    }

    val mapPaddingBottom = remember(bottomSheetOffset) {
        with(density) {
            if (screenHeight - bottomSheetOffset > 0.dp) {
                screenHeight - bottomSheetOffset + navigationBarHeightPx.toDp()
            } else {
                navigationBarHeightPx.toDp()
            }
        }
    }

    BackHandler(enabled = uiState.selectedVehicle != null) {
        onVehicleSelect(null)
    }

    LaunchedEffect(uiState.selectedVehicle) {
        if (uiState.selectedVehicle == null) {
            launch {
                scaffoldState.bottomSheetState.collapse()
            }
        }
    }

    val cameraPositionState = remember(uiState.initialCameraPosition) {
        CameraPositionState().apply {
            if (uiState.initialCameraPosition != null) {
                position = CameraPosition.fromLatLngZoom(uiState.initialCameraPosition, 13f)
//                    LatLng(54.4, 17.1),
//                    13f
            }
        }
    }

    val permissionRequestState = rememberRequestPermissionState(
        initRequest = false,
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    RequestPermission(
        context = context,
        requestState = permissionRequestState,
        onGranted = {
            Timber.d("granted")
            uiState.userLocation?.let {
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                it.toLatLng(),
                                cameraPositionState.position.zoom,
                                cameraPositionState.position.tilt,
                                cameraPositionState.position.bearing
                            )
                        ), 2000
                    )
                }
            }
        },
        onShowRationale = {
            Timber.d("show rationale")
            onOpenLocationRationaleDialog()
        },
        onPermanentlyDenied = {
            Timber.d("denied")
            onOpenLocationPermissionDialog()
        }
    )

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            cameraPositionState.projection?.visibleRegion?.latLngBounds?.let { bounds ->
                onLoadMapDataWithinBounds(bounds)
            }
        }
    }

    val visibleBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
    LaunchedEffect(visibleBounds != null) {
        if (visibleBounds != null) {
            onLoadMapDataWithinBounds(visibleBounds)
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = !uiState.isInRideMode,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            if (uiState.selectedVehicle != null) {
                SheetContent(
                    vehicleCode = uiState.selectedVehicle.code,
                    vehicleRange = uiState.selectedVehicle.range,
                    vehicleCharge = uiState.selectedVehicle.charge,
                    rideState = uiState.rideState,
                    onStartRideClick = onStartRideClick,
                    onFinishRideClick = onFinishRideClick
                )
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerElevation = 8.dp,
        drawerContent = {
            DrawerContent(
                username = uiState.username,
                userTotalDistance = uiState.userTotalDistance,
                userTotalRides = uiState.userTotalRides,
                onMyRidesClick = onMyRidesClick,
                onWalletClick = onWalletClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GlideMap(
                cameraPositionState = cameraPositionState,
                vehicleClusterItems = uiState.vehicleClusterItems,
                ridingZones = uiState.ridingZones,
                noParkingZones = uiState.noParkingZones,
                userLocation = uiState.userLocation,
                mapPaddingBottom = mapPaddingBottom,
                rideRoute = uiState.rideRoute,
                onVehicleSelect = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                        onVehicleSelect(it)
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bottomSheetOffset)
                        .padding(16.dp)
                ) {
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.TopStart),
                        backgroundColor = Color.White,
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        backgroundColor = Color.White,
                        onClick = {
                            onLocationButtonClick()
                            permissionRequestState.requestPermission = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.NearMe,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GlideAppTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            onLocationButtonClick = {},
            onOpenLocationPermissionDialog = {},
            onOpenLocationRationaleDialog = {},
            onVehicleSelect = {},
            onLoadMapDataWithinBounds = {},
            onStartRideClick = {},
            onFinishRideClick = {},
            onMyRidesClick = {},
            onWalletClick = {}
        )
    }
}
