package com.apsl.glideapp.core.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.dto.VehicleDto
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.common.models.Route
import com.apsl.glideapp.core.database.dao.RideCoordinatesDao
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.ride.RideCoordinates
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.model.RideEvent
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.network.http.GlideApi
import com.apsl.glideapp.core.network.websocket.WebSocketClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class RideRepositoryImpl @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val api: GlideApi,
    private val ridePager: Pager<Int, RideEntity>,
    private val rideCoordinatesDao: RideCoordinatesDao,
    appDataStore: AppDataStore
) : RideRepository {

    override val isRideModeActive: Flow<Boolean> = appDataStore.isRideModeActive.map { it ?: false }

    override val rideEvents: Flow<RideEvent> = webSocketClient.rideEvents.mapNotNull { dto ->
        when (dto) {
            is RideEventDto.Started, is RideEventDto.Restored -> {
                appDataStore.saveRideModeActive(value = true)
            }

            is RideEventDto.Error, is RideEventDto.Finished, is RideEventDto.SessionCancelled -> {
                appDataStore.saveRideModeActive(value = false)
            }

            else -> Unit
        }
        dto.toDomain()
    }

    private fun RideEventDto.toDomain(): RideEvent? {
        return when (this) {
            is RideEventDto.Started -> {
                RideEvent.Started(
                    rideId = this.rideId,
                    vehicle = this.vehicle.toDomain(),
                    dateTime = this.dateTime
                )
            }

            is RideEventDto.Restored -> {
                RideEvent.Started(
                    rideId = this.rideId,
                    vehicle = this.vehicle.toDomain(),
                    dateTime = this.startDateTime
                )
            }

            is RideEventDto.RouteUpdated -> RideEvent.RouteUpdated(this.currentRoute)
            is RideEventDto.Finished -> RideEvent.Finished

            is RideEventDto.Error.UserInsideNoParkingZone -> {
                RideEvent.Error.UserInsideNoParkingZone(this.message)
            }

            is RideEventDto.Error.UserTooFarFromVehicle -> {
                RideEvent.Error.UserTooFarFromVehicle(this.message)
            }

            is RideEventDto.Error.NotEnoughFunds -> {
                RideEvent.Error.NotEnoughFunds(this.message)
            }

            else -> null
        }
    }

    override suspend fun updateRideState(action: RideAction) {
        webSocketClient.sendRideAction(action)
    }

    override fun getUserRidesPaginated(): Flow<PagingData<Ride>> {
        return ridePager.flow.map { pagingData ->
            pagingData.map { entity ->
                val rideCoordinates = rideCoordinatesDao
                    .getRideCoordinatesByRideId(entity.id)
                    .map { Coordinates(it.latitude, it.longitude) }

                Ride(
                    id = entity.id,
                    startAddress = entity.startAddress,
                    finishAddress = entity.finishAddress,
                    startDateTime = entity.startDateTime,
                    finishDateTime = entity.finishDateTime,
                    route = Route(rideCoordinates),
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

    // Used ONLY for Paging3 workaround
    override fun getAllRideCoordinates(): Flow<List<RideCoordinates>> {
        return rideCoordinatesDao
            .getAllRideCoordinates()
            .map { flow -> flow.map { RideCoordinates(it.rideId, it.latitude, it.longitude) } }
    }

    private fun VehicleDto.toDomain(): Vehicle {
        return Vehicle(
            id = this.id,
            code = this.code,
            batteryCharge = this.batteryCharge,
            type = this.type,
            status = this.status,
            coordinates = this.coordinates,
            unlockingFee = this.unlockingFee,
            farePerMinute = this.farePerMinute
        )
    }
}
