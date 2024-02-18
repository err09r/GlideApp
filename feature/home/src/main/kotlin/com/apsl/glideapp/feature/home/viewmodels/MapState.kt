package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.UserLocation
import com.apsl.glideapp.feature.home.models.VehicleClusterItem
import com.apsl.glideapp.feature.home.models.ZoneUiModel
import com.google.android.gms.maps.model.LatLng

@Immutable
data class MapState(
    val userLocation: UserLocation? = null,
    val vehicleClusterItems: List<VehicleClusterItem> = emptyList(),
    val ridingZones: List<List<LatLng>> = emptyList(), // Designed like this because of 'Polygon' composable implementation
    val noParkingZones: List<ZoneUiModel> = emptyList(),
    val rideRoute: List<LatLng>? = null,
    val selectedVehicleRadiusMeters: Double? = null
)
