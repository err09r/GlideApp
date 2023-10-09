package com.apsl.glideapp.feature.home.maps

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng

@Immutable
data class NoParkingZone(
    val coordinates: List<LatLng>,
    val center: LatLng
)
