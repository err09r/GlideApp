package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.UserAuthState
import com.apsl.glideapp.feature.home.map.MapState
import com.apsl.glideapp.feature.home.screens.RideState
import com.apsl.glideapp.feature.home.screens.SelectedVehicleUiModel
import com.apsl.glideapp.feature.home.screens.UserInfo
import com.google.android.gms.maps.model.LatLng

@Immutable
data class HomeUiState(
    val userAuthState: UserAuthState = UserAuthState.Undefined,
    val userInfo: UserInfo = UserInfo(),
    val initialCameraPosition: LatLng? = null,
    val isLoadingMapContent: Boolean = false,
    val mapState: MapState = MapState(),
    val selectedVehicle: SelectedVehicleUiModel? = null,
    val rideState: RideState? = null,
    val error: HomeUiError? = null
) {
    val isRideActive: Boolean get() = rideState != null && !rideState.isPaused
}
