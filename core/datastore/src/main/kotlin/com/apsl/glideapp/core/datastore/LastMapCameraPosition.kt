package com.apsl.glideapp.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class LastMapCameraPosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float
)
