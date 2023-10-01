package com.apsl.glideapp.feature.home.maps

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.core.util.maps.toLatLng
import com.apsl.glideapp.core.util.maps.toLatLngBounds
import com.google.android.gms.maps.model.LatLng

@Immutable
data class NoParkingZone(val coordinates: List<LatLng>) {
    val center: LatLng = coordinates.toLatLngBounds().center
}

fun List<Coordinates>.mapToNoParkingZones() = NoParkingZone(this.map(Coordinates::toLatLng))
