package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.util.android.NumberFormatter
import com.apsl.glideapp.feature.home.models.SelectedVehicleUiModel
import com.google.android.gms.maps.model.LatLng

@Immutable
data class HomeUiState(
    val userInfo: UserInfo = UserInfo(),
    val initialCameraPosition: Pair<LatLng, Float>? = null,
    val isLoadingMapContent: Boolean = false,
    val mapState: MapState = MapState(),
    val selectedVehicle: SelectedVehicleUiModel? = null,
    val rideState: RideState? = null
) {
    val isRideActive: Boolean get() = rideState != null && !rideState.isPaused
}

@Immutable
data class UserInfo(
    val username: String? = null,
    val totalDistanceKilometers: String = NumberFormatter.default(),
    val totalRides: String = NumberFormatter.default(),
    val walletVisited: Boolean = false
)
