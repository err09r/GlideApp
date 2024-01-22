@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package com.apsl.glideapp.core.util.maps

import android.location.Location
import androidx.annotation.FloatRange
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.ln
import kotlin.math.max

object MapsUtils {

    private const val MIN_ZOOM_LEVEL = 3.0
    private const val MAX_ZOOM_LEVEL = 21.0
    private const val DEFAULT_MIN_ZOOM_LEVEL = MIN_ZOOM_LEVEL.toFloat()
    private const val DEFAULT_MAX_ZOOM_LEVEL = MAX_ZOOM_LEVEL.toFloat()

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

    fun calculateZoomLevel(
        bounds: LatLngBounds,
        @FloatRange(
            from = MIN_ZOOM_LEVEL,
            to = MAX_ZOOM_LEVEL
        ) maxZoom: Float = DEFAULT_MAX_ZOOM_LEVEL,
        @FloatRange(
            from = MIN_ZOOM_LEVEL,
            to = MAX_ZOOM_LEVEL
        ) minZoom: Float = DEFAULT_MIN_ZOOM_LEVEL
    ): Float {
        val center = bounds.center
        val scale = max(
            calculateDistance(center, bounds.southwest),
            calculateDistance(center, bounds.northeast)
        ) / 1000
        val baseZoomLevel = (minZoom + maxZoom) / 2 + 0.8f
        return baseZoomLevel - ln(scale) / ln(2f)
    }
}
