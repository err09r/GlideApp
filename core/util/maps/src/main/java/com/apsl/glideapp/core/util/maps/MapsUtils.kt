@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package com.apsl.glideapp.core.util.maps

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.ln
import kotlin.math.max

object MapsUtils {

    fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            start.latitude,
            start.longitude,
            end.latitude,
            end.longitude,
            results
        )
        return results.single()
    }

    fun calculateZoomLevel(bounds: LatLngBounds, maxZoom: Float = 21f, minZoom: Float = 3f): Float {
        val center = bounds.center
        val scale = max(
            calculateDistance(center, bounds.southwest),
            calculateDistance(center, bounds.northeast)
        ) / 1000
        val baseZoomLevel = (minZoom + maxZoom) / 2 + 0.8f
        return baseZoomLevel - ln(scale) / ln(2f)
    }
}
