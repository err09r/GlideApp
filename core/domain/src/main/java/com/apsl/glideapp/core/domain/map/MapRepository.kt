package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.CoordinatesBounds
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    val mapStateUpdates: Flow<MapState>
    suspend fun loadMapDataWithinBounds(bounds: CoordinatesBounds)
}
