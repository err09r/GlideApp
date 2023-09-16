@file:Suppress("FoldInitializerAndIfToElvis")

package com.apsl.glideapp.core.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apsl.glideapp.common.dto.RideDto
import com.apsl.glideapp.common.models.RideStatus
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.database.AppDatabase
import com.apsl.glideapp.core.database.entities.RideCoordinatesEntity
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.domain.connectivity.ConnectionState
import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime
import timber.log.Timber

@Singleton
class RideRemoteMediator @Inject constructor(
    private val api: GlideApi,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) : RemoteMediator<Int, RideEntity>() {

    private val rideDao = appDatabase.rideDao()
    private val rideCoordinatesDao = appDatabase.rideCoordinatesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RideEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    ((lastItem.key ?: 1) / state.config.pageSize) + 1
                }
            }

            if (connectivityObserver.connectivityState.firstOrNull() == ConnectionState.Available) {
                return if (rideDao.isTableEmpty()) {
                    MediatorResult.Error(Exception("Ride table is empty"))
                } else {
                    MediatorResult.Success(true)
                }
            }

            Timber.d("Loadkey: $loadKey")

            val rideDtos = api.getUserRidesByStatus(
                status = RideStatus.Finished.name,
                page = loadKey,
                limit = state.config.pageSize
            )

            val rideEntities = rideDtos.mapToEntities()
            val rideCoordinatesEntities = rideDtos.mapToRideCoordinatesEntities()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rideDao.deleteAllRides()
                    rideCoordinatesDao.deleteAllRideCoordinates()
                }

                rideDao.upsertRides(rideEntities)
                rideCoordinatesDao.upsertRideCoordinates(rideCoordinatesEntities)
            }
            MediatorResult.Success(endOfPaginationReached = rideDtos.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun List<RideDto>.mapToEntities(): List<RideEntity> {
        return this.map {
            RideEntity(
                id = it.id,
                key = it.key,
                startAddress = it.startAddress,
                finishAddress = it.finishAddress,
                startDateTime = it.startDateTime,
                finishDateTime = it.finishDateTime,
                distance = it.distance,
                averageSpeed = it.averageSpeed,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        }
    }

    private fun List<RideDto>.mapToRideCoordinatesEntities(): List<RideCoordinatesEntity> {
        return this.flatMap { ride ->
            ride.route.map { coordinates ->
                RideCoordinatesEntity(
                    rideId = ride.id,
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            }
        }
    }
}
