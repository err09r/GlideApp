package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.core.model.MapCameraPosition
import com.apsl.glideapp.core.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val userLocation: Flow<Result<UserLocation>>
    suspend fun saveLastMapCameraPosition(position: MapCameraPosition)
    suspend fun getLastMapCameraPosition(): MapCameraPosition?
}
