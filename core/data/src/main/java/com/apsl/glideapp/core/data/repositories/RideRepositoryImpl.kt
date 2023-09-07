package com.apsl.glideapp.core.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.ride.Ride
import com.apsl.glideapp.core.domain.ride.RideEvent
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.network.GlideApi
import com.apsl.glideapp.core.network.WebSocketClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class RideRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val api: GlideApi,
    private val ridePager: Pager<Int, RideEntity>,
    appDataStore: AppDataStore
) : RideRepository {

    override val isRideModeActive: Flow<Boolean> =
        appDataStore.getRideModeActive().map { it ?: false }

    override val rideEvents: Flow<RideEvent> = webSocketClient.rideEvents.mapNotNull {
        when (it) {
            is RideEventDto.Started -> RideEvent.Started(it.rideId, it.dateTime)
            is RideEventDto.Restored -> RideEvent.Started(it.rideId, it.dateTime)
            is RideEventDto.RouteUpdated -> RideEvent.RouteUpdated(it.currentRoute)
            is RideEventDto.Finished -> RideEvent.Finished
            is RideEventDto.Error -> {
                when (it) {
                    is RideEventDto.Error.UserInsideNoParkingZone -> {
                        RideEvent.Error.UserInsideNoParkingZone(it.message)
                    }
                }
            }

            is RideEventDto.SessionCancelled -> {
                appDataStore.saveRideModeActive(value = false)
                null
            }
        }
    }

    override suspend fun updateRideState(action: RideAction) {
        webSocketClient.sendRideAction(action)
    }

    override fun getUserRidesPaginated(): Flow<PagingData<Ride>> {
        return ridePager.flow.map { pagingData ->
            pagingData.map { dto ->
                Ride(
                    id = dto.id,
                    startAddress = dto.startAddress,
                    finishAddress = dto.finishAddress,
                    startDateTime = dto.startDateTime,
                    finishDateTime = dto.finishDateTime,
                    route = dto.route,
                    distance = dto.distance,
                    averageSpeed = dto.averageSpeed
                )
            }
        }
    }

    override suspend fun getRideById(id: String): Ride {
        val dto = api.getRideById(id)
        return Ride(
            id = dto.id,
            startAddress = dto.startAddress,
            finishAddress = dto.finishAddress,
            startDateTime = dto.startDateTime,
            finishDateTime = dto.finishDateTime,
            route = dto.route,
            distance = dto.distance,
            averageSpeed = dto.averageSpeed
        )
    }
}
