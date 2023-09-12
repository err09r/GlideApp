package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.domain.home.Vehicle
import com.apsl.glideapp.core.domain.map.MapRepository
import com.apsl.glideapp.core.domain.map.RemoteMapContent
import com.apsl.glideapp.core.network.WebSocketClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient
) : MapRepository {

    override val remoteMapContent: Flow<RemoteMapContent> =
        webSocketClient.mapContent.map { mapContentDto ->
            val availableVehicles = mapContentDto.availableVehicles.map {
                Vehicle(
                    id = it.id,
                    code = it.code,
                    batteryCharge = it.batteryCharge,
                    type = it.type,
                    status = it.status,
                    coordinates = it.coordinates,
                )
            }
            RemoteMapContent(availableVehicles = availableVehicles)
        }

    override suspend fun getMapContentWithinBounds(bounds: CoordinatesBounds) {
        webSocketClient.sendMapData(data = bounds)
    }
}
