package com.apsl.glideapp.core.model

data class UserLocation(
    val provider: String?,
    val timeMs: Long,
    val elapsedRealtimeNs: Long,
    val latitudeDegrees: Double,
    val longitudeDegrees: Double,
    val horizontalAccuracyMeters: Float,
    val altitudeMeters: Double,
    val altitudeAccuracyMeters: Float,
    val speedMetersPerSecond: Float,
    val speedAccuracyMetersPerSecond: Float,
    val bearingDegrees: Float,
    val bearingAccuracyDegrees: Float
)
