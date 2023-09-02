package com.apsl.glideapp.core.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationClient {
    val userLocation: Flow<UserLocation>
    suspend fun startReceivingLocationUpdates(locationUpdateIntervalMs: Long)
    suspend fun stopReceivingLocationUpdates()

    companion object {
        const val DEFAULT_LOCATION_REQUEST_INTERVAL_MS = 5000L
    }
}
