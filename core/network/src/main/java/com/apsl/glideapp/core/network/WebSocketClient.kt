package com.apsl.glideapp.core.network

import com.apsl.glideapp.common.dto.MapStateDto
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.common.models.RideAction
import kotlinx.coroutines.flow.Flow

interface WebSocketClient {
    fun getMapStateUpdates(authToken: String?): Flow<MapStateDto>
    fun getRideStateUpdates(authToken: String?): Flow<RideEventDto>
    suspend fun sendMapData(data: CoordinatesBounds)
    suspend fun sendRideAction(action: RideAction)
    suspend fun closeMapSession()
    suspend fun closeRideSession()
}
