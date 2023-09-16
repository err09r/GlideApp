package com.apsl.glideapp.core.network.websocket

import com.apsl.glideapp.common.dto.MapContentDto
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.common.models.RideAction
import kotlinx.coroutines.flow.Flow

interface WebSocketClient {
    val mapContent: Flow<MapContentDto>
    val rideEvents: Flow<RideEventDto>
    suspend fun sendMapData(data: CoordinatesBounds)
    suspend fun sendRideAction(action: RideAction)
}
