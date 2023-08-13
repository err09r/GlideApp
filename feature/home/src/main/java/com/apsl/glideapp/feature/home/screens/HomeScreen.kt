package com.apsl.glideapp.feature.home.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.collectWithLifecycle
import com.apsl.glideapp.core.ui.getOffset
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.launchForLocationPermissions
import com.apsl.glideapp.core.util.locationPermissionsGranted
import com.apsl.glideapp.core.util.showLocationRequestPermissionRationale
import com.apsl.glideapp.feature.home.RideService
import com.apsl.glideapp.feature.home.components.DrawerContent
import com.apsl.glideapp.feature.home.components.SheetContent
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.apsl.glideapp.feature.home.maps.toLatLng
import com.apsl.glideapp.feature.home.viewmodels.HomeAction
import com.apsl.glideapp.feature.home.viewmodels.HomeUiState
import com.apsl.glideapp.feature.home.viewmodels.HomeViewModel
import com.apsl.glideapp.feature.home.viewmodels.UserAuthState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
    onNavigateToLocationPermission: () -> Unit
) {
    viewModel.run {
        mapState.collectWithLifecycle()
        userLocation.collectWithLifecycle()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.userAuthState) {
        if (uiState.userAuthState == UserAuthState.NotAuthenticated) {
            onNavigateToLogin()
        }
    }

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

    if (uiState.userAuthState == UserAuthState.Authenticated) {
        HomeScreenContent(
            uiState = uiState,
            onFirstTimeLocationPermissionRequested = viewModel::saveLocationRequestRationaleWasShown,
            onOpenLocationPermissionDialog = onNavigateToLocationPermission,
            onVehicleSelect = viewModel::updateSelectedVehicle,
            onLoadContentWithinBounds = viewModel::loadContentWithinBounds,
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
    onFirstTimeLocationPermissionRequested: () -> Unit,
    onOpenLocationPermissionDialog: () -> Unit,
    onVehicleSelect: (VehicleClusterItem?) -> Unit,
    onLoadContentWithinBounds: (LatLngBounds) -> Unit,
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

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { onFirstTimeLocationPermissionRequested() }
    )

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
                position = CameraPosition.fromLatLngZoom(
//                            LatLng(
//                                uiState.initialCameraPosition.latitude,
//                                uiState.initialCameraPosition.longitude
//                            ), 13f
                    LatLng(54.4, 17.1),
                    13f
                )
            }
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            cameraPositionState.projection?.visibleRegion?.latLngBounds?.let { bounds ->
                onLoadContentWithinBounds(bounds)
            }
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
                            if (context.showLocationRequestPermissionRationale) {
                                locationPermissionResultLauncher.launchForLocationPermissions()
                                Timber.d("1")
                            } else {
                                if (context.locationPermissionsGranted) {
                                    Timber.d("2")
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
                                } else {
                                    if (uiState.wasLocationRequestRationaleShown) {
                                        Timber.d("3")
                                        onOpenLocationPermissionDialog()
                                    } else {
                                        Timber.d("4")
                                        locationPermissionResultLauncher.launchForLocationPermissions()
                                    }
                                }
                            }
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
            onFirstTimeLocationPermissionRequested = {},
            onOpenLocationPermissionDialog = {},
            onVehicleSelect = {},
            onLoadContentWithinBounds = {},
            onStartRideClick = {},
            onFinishRideClick = {},
            onMyRidesClick = {},
            onWalletClick = {}
        )
    }
}
