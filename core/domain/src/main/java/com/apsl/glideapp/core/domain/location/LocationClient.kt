package com.apsl.glideapp.core.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationClient {
    val userLocation: Flow<UserLocation>
    fun setLocationUpdateInterval(intervalMillis: Long)
}
