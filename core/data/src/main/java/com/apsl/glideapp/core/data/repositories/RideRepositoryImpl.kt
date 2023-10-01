package com.apsl.glideapp.core.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.common.models.Route
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.model.RideEvent
import com.apsl.glideapp.core.network.http.GlideApi
import com.apsl.glideapp.core.network.websocket.WebSocketClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class RideRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val api: GlideApi,
    private val ridePager: Pager<Int, RideEntity>,
    appDataStore: AppDataStore
) : RideRepository {

    override val isRideModeActive: Flow<Boolean> = appDataStore.isRideModeActive.map { it ?: false }

    override val rideEvents: Flow<RideEvent> = webSocketClient.rideEvents.mapNotNull {
        when (it) {
            is RideEventDto.Started -> {
                appDataStore.saveRideModeActive(value = true)
                RideEvent.Started(it.rideId, it.dateTime)
            }

            is RideEventDto.Restored -> {
                appDataStore.saveRideModeActive(value = true)
                RideEvent.Started(it.rideId, it.dateTime)
            }

            is RideEventDto.RouteUpdated -> RideEvent.RouteUpdated(it.currentRoute)
            is RideEventDto.Finished -> {
                appDataStore.saveRideModeActive(value = false)
                RideEvent.Finished
            }

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
    }.onCompletion { Timber.d("ride rep compl") }

    override suspend fun updateRideState(action: RideAction) {
        webSocketClient.sendRideAction(action)
    }

    override fun getUserRidesPaginated(): Flow<PagingData<Ride>> {
        return ridePager.flow.map { pagingData ->
            pagingData.map { entity ->
                Ride(
                    id = entity.id,
                    startAddress = entity.startAddress,
                    finishAddress = entity.finishAddress,
                    startDateTime = entity.startDateTime,
                    finishDateTime = entity.finishDateTime,
                    route = Route(emptyList()), //Route(entity.),
                    averageSpeed = entity.averageSpeed
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
            averageSpeed = dto.averageSpeed
        )
    }
}
