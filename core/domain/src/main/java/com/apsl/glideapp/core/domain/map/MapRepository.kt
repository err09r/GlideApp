package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.CoordinatesBounds
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getMapStateUpdates(): Flow<MapState>
    suspend fun loadMapContentWithinBounds(bounds: CoordinatesBounds)
}
