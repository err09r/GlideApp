package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.home.Vehicle
import com.apsl.glideapp.core.domain.home.Zone
import com.apsl.glideapp.core.domain.map.MapRepository
import com.apsl.glideapp.core.domain.map.MapState
import com.apsl.glideapp.core.network.WebSocketClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val appDataStore: AppDataStore
) : MapRepository {

    override val mapStateUpdates: Flow<MapState> =
        webSocketClient.mapStateUpdates.map { mapStateDto ->
            MapState(
                availableVehicles = mapStateDto.availableVehicles.map { vehicleDto ->
                    Vehicle(
                        id = vehicleDto.id,
                        code = vehicleDto.code,
                        batteryCharge = vehicleDto.batteryCharge,
                        status = vehicleDto.status,
                        coordinates = vehicleDto.coordinates
                    )
                },
                ridingZones = mapStateDto.ridingZones.map { zoneDto ->
                    Zone(
                        id = zoneDto.id,
                        code = zoneDto.code,
                        title = zoneDto.title,
                        coordinates = zoneDto.coordinates
                    )
                },
                noParkingZones = mapStateDto.noParkingZones.map { zoneDto ->
                    Zone(
                        id = zoneDto.id,
                        code = zoneDto.code,
                        title = zoneDto.title,
                        coordinates = zoneDto.coordinates
                    )
                }
            )
        }

    override suspend fun loadMapDataWithinBounds(bounds: CoordinatesBounds) {
        webSocketClient.sendMapData(data = bounds)
    }
}
