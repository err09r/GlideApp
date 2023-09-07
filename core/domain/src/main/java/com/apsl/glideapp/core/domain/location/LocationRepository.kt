package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.models.Coordinates
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val userLocation: Flow<Result<UserLocation>>
    suspend fun saveLastUserLocation(location: Coordinates)
    suspend fun getLastSavedUserLocation(): Coordinates?
}
