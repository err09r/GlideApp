package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.core.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    val userLocation: Flow<Result<UserLocation>>
    val isGpsEnabled: Boolean

    companion object {
        const val DEFAULT_LOCATION_REQUEST_INTERVAL_MS = 5000L
        const val RIDE_MODE_LOCATION_REQUEST_INTERVAL_MS = 1000L
        const val LOCATION_UPDATE_MIN_DISTANCE = 0.8f
    }
}
