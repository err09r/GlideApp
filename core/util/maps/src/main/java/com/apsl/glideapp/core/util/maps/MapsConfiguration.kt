@file:Suppress("Unused")

package com.apsl.glideapp.core.util.maps

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object MapsConfiguration {
    const val VEHICLE_CIRCLE_VISIBILITY_ZOOM_LEVEL: Float = 15.5f
    const val NO_PARKING_ZONE_VISIBILITY_ZOOM_LEVEL: Float = 13f

    val mapBorders by lazy {
        listOf(
            LatLng(90.0, 0.0),
            LatLng(90.0, 90.0),
            LatLng(-90.0, 90.0),
            LatLng(-90.0, 0.0)
        )
    }

    val homeCameraBounds by lazy {
        LatLngBounds(LatLng(48.45, 13.9), LatLng(55.75, 23.15))
    }
}
