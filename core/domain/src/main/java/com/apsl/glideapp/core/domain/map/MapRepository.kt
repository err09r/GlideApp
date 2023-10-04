package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.model.RemoteMapContent
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    val remoteMapContent: Flow<RemoteMapContent>
    suspend fun getMapContentWithinBounds(bounds: CoordinatesBounds)
}
