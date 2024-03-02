package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.domain.map.MapRepository
import com.apsl.glideapp.core.model.BatteryState
import com.apsl.glideapp.core.model.RemoteMapContent
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.network.websocket.WebSocketClient
import com.apsl.glideapp.core.util.android.CurrencyFormatter
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient
) : MapRepository {

    override val remoteMapContent: Flow<RemoteMapContent> =
        webSocketClient.mapContent.map { mapContentDto ->
            val availableVehicles = mapContentDto.availableVehicles.map { dto ->
                Vehicle(
                    id = dto.id,
                    code = dto.code.toString().padStart(4, '0'),
                    type = dto.type,
                    status = dto.status,
                    coordinates = dto.coordinates,
                    rangeKilometers = (dto.batteryCharge * 0.4).roundToInt().toString(),
                    unlockingFee = CurrencyFormatter.format(dto.unlockingFee),
                    farePerMinute = CurrencyFormatter.format(dto.farePerMinute),
                    batteryCharge = dto.batteryCharge.toString(),
                    batteryState = when (dto.batteryCharge) {
                        in 0..34 -> BatteryState.Low
                        in 35..84 -> BatteryState.Medium
                        in 85..100 -> BatteryState.Full
                        else -> BatteryState.Undefined
                    }
                )
            }
            RemoteMapContent(availableVehicles = availableVehicles)
        }

    override suspend fun getMapContentWithinBounds(bounds: CoordinatesBounds) {
        webSocketClient.sendMapData(data = bounds)
    }
}
