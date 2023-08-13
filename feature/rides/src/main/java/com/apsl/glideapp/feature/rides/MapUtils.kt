package com.apsl.glideapp.feature.rides

import com.apsl.glideapp.common.models.Coordinates
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

fun List<Coordinates>.toLatLngBounds(): LatLngBounds {
    val topmostPoint = this.maxBy { it.latitude }
    val bottommostPoint = this.minBy { it.latitude }
    val leftmostPoint = this.minBy { it.longitude }
    val rightmostPoint = this.maxBy { it.longitude }

    val southwest = LatLng(bottommostPoint.latitude, leftmostPoint.longitude)
    val northeast = LatLng(topmostPoint.latitude, rightmostPoint.longitude)

    return LatLngBounds(southwest, northeast)
}
