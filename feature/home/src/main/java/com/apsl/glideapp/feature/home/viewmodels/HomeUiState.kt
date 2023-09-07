package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.domain.auth.UserAuthState
import com.apsl.glideapp.core.domain.location.UserLocation
import com.apsl.glideapp.feature.home.maps.NoParkingZone
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.google.android.gms.maps.model.LatLng

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val userAuthState: UserAuthState = UserAuthState.Undefined,
    val userTotalDistance: Int = 0,
    val userTotalRides: Int = 0,
    val username: String? = null,
    val userBalance: Double = 0.0,
    val selectedVehicle: VehicleClusterItem? = null,
    val vehicleClusterItems: List<VehicleClusterItem> = emptyList(),
    val ridingZones: List<List<LatLng>> = emptyList(),
    val noParkingZones: List<NoParkingZone> = emptyList(),
    val initialCameraPosition: LatLng? = null,
    val userLocation: UserLocation? = null,
    val wasLocationPermissionRequested: Boolean = false,
    val rideState: RideState? = null,
    val rideRoute: List<LatLng>? = null,
    val error: HomeUiError? = null
) {
    val isInRideMode: Boolean get() = rideState == RideState.Started
}

enum class RideState {
    Started, Paused
}
