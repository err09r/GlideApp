package com.apsl.glideapp.feature.home.screens

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.model.UserAuthState
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.LoadingBar
import com.apsl.glideapp.core.ui.RequestMultiplePermissions
import com.apsl.glideapp.core.ui.RequestMultiplePermissionsState
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Gps
import com.apsl.glideapp.core.ui.icons.Menu
import com.apsl.glideapp.core.ui.navigationBarHeight
import com.apsl.glideapp.core.ui.offset
import com.apsl.glideapp.core.ui.rememberRequestMultiplePermissionState
import com.apsl.glideapp.core.ui.screenHeight
import com.apsl.glideapp.core.ui.statusBarHeight
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toDp
import com.apsl.glideapp.core.util.maps.toLatLng
import com.apsl.glideapp.feature.home.components.HomeBottomSheet
import com.apsl.glideapp.feature.home.components.HomeDrawerSheet
import com.apsl.glideapp.feature.home.map.VehicleClusterItem
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
    onNavigateToLocationPermission: () -> Unit,
    onNavigateToLocationRationale: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.userAuthState) {
        UserAuthState.NotAuthenticated -> onNavigateToLogin()
        UserAuthState.Authenticated -> {
            val requestPermissionsState = rememberRequestMultiplePermissionState(
                permissions = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            HomeActionsHandler(
                actions = viewModel.actions,
                onStartObservingUserLocation = viewModel::startObservingUserLocation,
                onRequestLocationPermissions = { requestPermissionsState.requestPermissions = true }
            )
            ComposableLifecycle { event ->
                with(viewModel) {
                    when (event) {
                        Lifecycle.Event.ON_START -> {
                            getUser()
                            startReceivingRideEvents()
                            startObservingMapContent()
                            startObservingUserLocation()
                        }

                        Lifecycle.Event.ON_STOP -> {
                            stopObservingMapContent()
                            stopObservingUserLocation()
                        }

                        else -> Unit
                    }
                }
            }
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
                onMyRidesClick = onNavigateToAllRides,
                onWalletClick = onNavigateToWallet
            )
        }

        else -> Unit
    }
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    requestPermissionsState: RequestMultiplePermissionsState,
    onRefreshUserData: () -> Unit,
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

    BackHandler(enabled = uiState.selectedVehicle != null) {
        onVehicleSelect(null)
    }

//    LaunchedEffect(uiState.selectedVehicle) {
//        if (uiState.selectedVehicle == null) {
//            scaffoldState.bottomSheetState.hide()
//        } else {
//            scaffoldState.bottomSheetState.show()
//        }
//    }

//    LaunchedEffect(uiState.rideState) {
//        if (uiState.rideState == RideState.Active) {
//            scaffoldState.bottomSheetState.expand()
//        }
//    }

    val cameraPositionState = remember(uiState.initialCameraPosition) {
        CameraPositionState().apply {
            if (uiState.initialCameraPosition != null) {
                position = CameraPosition.fromLatLngZoom(uiState.initialCameraPosition, 13f)
//                    LatLng(54.4, 17.1),
//                    13f
            }
        }
    }

    LaunchedEffect(uiState.userLocation) {
        if (uiState.isInRideMode) {
            uiState.userLocation?.let {
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
            Timber.d("granted")
            uiState.userLocation?.let {
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

    //TODO to be completely refactored to load data on first dispay
    //TODO **********************************************************
    val visibleBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            visibleBounds?.let { onLoadMapDataWithinBounds(it) }
        }
    }
//
//    LaunchedEffect(cameraPositionState.projection) {
//        Timber.d("load block")
//        if (visibleBounds != null && uiState.vehicleClusterItems.isEmpty()) {
//            Timber.d("onLoad: $visibleBounds")
//            onLoadMapDataWithinBounds(visibleBounds)
//        }
//    }
    //TODO **********************************************************

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            HomeDrawerSheet(
                username = uiState.username,
                userTotalDistance = uiState.userTotalDistance,
                userTotalRides = uiState.userTotalRides,
                onMyRidesClick = onMyRidesClick,
                onWalletClick = onWalletClick
            )
        }
    ) {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = true,
                initialValue = SheetValue.Expanded
            )
        )
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetSwipeEnabled = !uiState.isInRideMode,
            sheetPeekHeight = 0.dp,
            sheetShape = RectangleShape,
            sheetContent = {
                HomeBottomSheet(
                    vehicleCode = uiState.selectedVehicle?.code ?: "",
                    vehicleRange = uiState.selectedVehicle?.range ?: 0,
                    vehicleCharge = uiState.selectedVehicle?.charge ?: 0,
                    rideState = uiState.rideState,
                    onStartRideClick = onStartRideClick,
                    onFinishRideClick = onFinishRideClick
                )
            }
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
                    vehicleClusterItems = uiState.vehicleClusterItems,
                    ridingZones = uiState.ridingZones,
                    noParkingZones = uiState.noParkingZones,
                    userLocation = uiState.userLocation,
                    contentPadding = PaddingValues(bottom = mapPaddingBottom),
                    rideRoute = uiState.rideRoute,
                    onVehicleSelect = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                            onVehicleSelect(it)
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding()
                        .background(Color.Red.copy(0.1f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(imageVector = GlideIcons.Menu, contentDescription = null)
                            }
                            if (uiState.isLoadingMapContent) {
                                Spacer(Modifier.width(16.dp))
                                LoadingBar(modifier = Modifier.weight(1f))
                                Spacer(Modifier.width(88.dp))
                            } else {
                                Spacer(Modifier.weight(1f))
                            }
                        }

                        FloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            onClick = {
                                onLocationButtonClick()
                                requestPermissionsState.requestPermissions = true
                            }
                        ) {
                            Icon(imageVector = GlideIcons.Gps, contentDescription = null)
                        }
                    }
                    Spacer(
                        Modifier
                            .height(bottomSheetHeight)
                            .fillMaxWidth()
                            .background(Color.Green)
                    )
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
            onWalletClick = {}
        )
    }
}
