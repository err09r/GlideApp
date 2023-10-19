package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.UserAuthState
import com.apsl.glideapp.feature.home.map.MapState
import com.apsl.glideapp.feature.home.screens.SelectedVehicleUiModel
import com.google.android.gms.maps.model.LatLng

@Immutable
data class HomeUiState(
    val userAuthState: UserAuthState = UserAuthState.Undefined,
    val userTotalDistance: Int = 0,
    val userTotalRides: Int = 0,
    val username: String? = null,
    val userBalance: Double = 0.0,
    val initialCameraPosition: LatLng? = null,
    val isLoadingMapContent: Boolean = false,
    val mapState: MapState = MapState(),
    val selectedVehicle: SelectedVehicleUiModel? = null,
    val rideState: RideState? = null,
    val error: HomeUiError? = null
) {
    val isInRideMode: Boolean get() = rideState == RideState.Active
}

enum class RideState {
    Active, Paused
}
