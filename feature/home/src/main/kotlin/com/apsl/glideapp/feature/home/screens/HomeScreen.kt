package com.apsl.glideapp.feature.home.screens

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.RequestMultiplePermissions
import com.apsl.glideapp.core.ui.RequestMultiplePermissionsState
import com.apsl.glideapp.core.ui.navigationBarHeight
import com.apsl.glideapp.core.ui.offset
import com.apsl.glideapp.core.ui.rememberRequestMultiplePermissionState
import com.apsl.glideapp.core.ui.screenHeight
import com.apsl.glideapp.core.ui.statusBarHeight
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toDp
import com.apsl.glideapp.core.util.android.areNotificationsEnabled
import com.apsl.glideapp.core.util.maps.toLatLng
import com.apsl.glideapp.feature.home.map.GlideMap
import com.apsl.glideapp.feature.home.viewmodels.HomeUiState
import com.apsl.glideapp.feature.home.viewmodels.HomeViewModel
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
    onNavigateToTopUp: () -> Unit,
    onNavigateToLocationPermission: () -> Unit,
    onNavigateToLocationRationale: () -> Unit,
    onNavigateToNotificationPermission: () -> Unit
) {
    val context = LocalContext.current

    val requestPermissionsState = rememberRequestMultiplePermissionState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    HomeActionsHandler(
        actions = viewModel.actions,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToTopUp = onNavigateToTopUp,
        onStartObservingUserLocation = viewModel::startObservingUserLocation,
        onRequestLocationPermissions = { requestPermissionsState.requestPermissions = true }
    )

    ComposableLifecycle { event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                viewModel.run {
                    getUser()
                    startReceivingRideEvents()
                    startObservingMapContent()
                    startObservingUserLocation()
                    if (!context.areNotificationsEnabled) {
                        onNavigateToNotificationPermission()
                    }
                }
            }

            Lifecycle.Event.ON_STOP -> {
                viewModel.run {
                    stopObservingMapContent()
                    stopObservingUserLocation()
                }
            }

            else -> Unit
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        requestPermissionsState = requestPermissionsState,
        onRefreshUserData = viewModel::getUser,
        onLocationButtonClick = viewModel::startObservingUserLocation,
        onOpenLocationPermissionDialog = onNavigateToLocationPermission,
        onOpenLocationRationaleDialog = onNavigateToLocationRationale,
        onVehicleSelect = viewModel::updateSelectedVehicle,
        onLoadMapDataWithinBounds = viewModel::loadMapContentWithinBounds,
        onStartRideClick = viewModel::startRide,
        onFinishRideClick = viewModel::finishRide,
        onMyRidesClick = {
            viewModel.updateSelectedVehicle(null)
            onNavigateToAllRides()
        },
        onWalletClick = {
            viewModel.updateSelectedVehicle(null)
            onNavigateToWallet()
        },
        onLogoutClick = viewModel::logOut
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    requestPermissionsState: RequestMultiplePermissionsState,
    onRefreshUserData: () -> Unit,
    onLocationButtonClick: () -> Unit,
    onOpenLocationPermissionDialog: () -> Unit,
    onOpenLocationRationaleDialog: () -> Unit,
    onVehicleSelect: (String?) -> Unit,
    onLoadMapDataWithinBounds: (LatLngBounds) -> Unit,
    onStartRideClick: () -> Unit,
    onFinishRideClick: () -> Unit,
    onMyRidesClick: () -> Unit,
    onWalletClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val mapState = uiState.mapState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    BackHandler(enabled = uiState.selectedVehicle != null) {
        onVehicleSelect(null)
    }

    val cameraPositionState = remember(uiState.initialCameraPosition) {
        CameraPositionState().apply {
            if (uiState.initialCameraPosition != null) {
                position = CameraPosition.fromLatLngZoom(uiState.initialCameraPosition, 13f)
            }
        }
    }

    LaunchedEffect(mapState.userLocation) {
        if (uiState.isRideActive) {
            mapState.userLocation?.let {
                val cameraPosition = CameraPosition.builder()
                    .zoom(17f)
                    .target(it.toLatLng())
                    .bearing(it.bearingDegrees)
                    .tilt(30f)
                    .build()
                cameraPositionState.animate(
                    CameraUpdateFactory.newCameraPosition(cameraPosition),
                    500
                )
            }
        }
    }

    RequestMultiplePermissions(
        context = context,
        requestState = requestPermissionsState,
        onGranted = {
            mapState.userLocation?.let {
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                it.toLatLng(),
                                cameraPositionState.position.zoom,
                                0f,
                                0f
                            )
                        ), 1000
                    )
                }
            }
        },
        onShowRationale = onOpenLocationRationaleDialog,
        onPermanentlyDenied = onOpenLocationPermissionDialog
    )

    //TODO to be completely refactored to load data on first display
    val visibleBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            visibleBounds?.let { onLoadMapDataWithinBounds(it) }
        }
    }

//    LaunchedEffect(cameraPositionState.projection) {
//        Timber.d("load block")
//        if (visibleBounds != null && uiState.vehicleClusterItems.isEmpty()) {
//            Timber.d("onLoad: $visibleBounds")
//            onLoadMapDataWithinBounds(visibleBounds)
//        }
//    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            HomeDrawerSheet(
                userInfo = uiState.userInfo,
                onMyRidesClick = onMyRidesClick,
                onWalletClick = onWalletClick,
                onLogoutClick = onLogoutClick
            )
        }
    ) {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
        )

        LaunchedEffect(uiState.rideState?.vehicle) {
            Timber.d("uiState.rideState?.vehicle: ${uiState.rideState?.vehicle}")
            if (uiState.rideState?.vehicle == null) {
                scaffoldState.bottomSheetState.hide()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }

        LaunchedEffect(uiState.selectedVehicle) {
            Timber.d("uiState.selectedVehicle: ${uiState.selectedVehicle}")
            if (uiState.selectedVehicle == null && !uiState.isRideActive) {
                scaffoldState.bottomSheetState.hide()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }

        BottomSheetScaffold(
            sheetContent = {
                when {
                    uiState.selectedVehicle != null && !uiState.isRideActive -> {
                        DefaultSheetLayout(
                            selectedVehicle = uiState.selectedVehicle,
                            onStartRideClick = onStartRideClick
                        )
                    }

                    uiState.rideState != null -> {
                        ActiveRideSheetLayout(
                            vehicle = uiState.rideState.vehicle,
                            onFinishRideClick = onFinishRideClick
                        )
                    }
                }
            },
            scaffoldState = scaffoldState,
            sheetShape = if (uiState.isRideActive) RectangleShape else BottomSheetDefaults.ExpandedShape,
            sheetPeekHeight = 0.dp,
            sheetShadowElevation = 8.dp,
            sheetDragHandle = if (!uiState.isRideActive) {
                { BottomSheetDefaults.DragHandle() }
            } else {
                null
            },
            sheetSwipeEnabled = !uiState.isRideActive
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                val statusBarHeight = WindowInsets.statusBarHeight
                val navigationBarHeight = WindowInsets.navigationBarHeight
                val bottomSheetOffset = scaffoldState.bottomSheetState.offset.toDp()
                val bottomSheetHeight = screenHeight - bottomSheetOffset + statusBarHeight
                val mapPaddingBottom = if (bottomSheetHeight > 0.dp) {
                    bottomSheetHeight + navigationBarHeight
                } else {
                    navigationBarHeight
                }

                GlideMap(
                    cameraPositionState = cameraPositionState,
                    mapState = uiState.mapState,
                    selectedVehicle = uiState.selectedVehicle,
                    contentPadding = PaddingValues(bottom = mapPaddingBottom),
                    onVehicleSelect = { onVehicleSelect(it) }
                )
                MapOverlayLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    height = bottomSheetOffset - statusBarHeight,
                    showLoading = uiState.isLoadingMapContent,
                    onMenuClick = remember {
                        {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    },
                    onMyLocationClick = remember {
                        {
                            onLocationButtonClick()
                            requestPermissionsState.requestPermissions = true
                        }
                    }
                )
                if (uiState.isRideActive && uiState.rideState != null) {
                    RideLayout(rideState = uiState.rideState)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    GlideAppTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            requestPermissionsState = rememberRequestMultiplePermissionState(permissions = emptyList()),
            onRefreshUserData = {},
            onLocationButtonClick = {},
            onOpenLocationPermissionDialog = {},
            onOpenLocationRationaleDialog = {},
            onVehicleSelect = {},
            onLoadMapDataWithinBounds = {},
            onStartRideClick = {},
            onFinishRideClick = {},
            onMyRidesClick = {},
            onWalletClick = {},
            onLogoutClick = {}
        )
    }
}
