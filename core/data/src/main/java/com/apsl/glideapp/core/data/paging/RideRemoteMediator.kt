package com.apsl.glideapp.core.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.database.AppDatabase
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.domain.connectivity.isConnected
import com.apsl.glideapp.core.network.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime
import timber.log.Timber

class RideRemoteMediator @Inject constructor(
    private val api: GlideApi,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) : RemoteMediator<Int, RideEntity>() {

    private val rideDao = appDatabase.rideDao()

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

            if (connectivityObserver.connectivityState.firstOrNull()?.isConnected == false) {
                return if (rideDao.isTableEmpty()) {
                    MediatorResult.Error(Exception("HAHAHA"))
                } else {
                    MediatorResult.Success(true)
                }
            }

            Timber.d("Loadkey: $loadKey")

            val rideDtos = api.getUserRides(page = loadKey, limit = state.config.pageSize)

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rideDao.deleteAllRides()
                }

                val rideEntities = rideDtos.map {
                    RideEntity(
                        id = it.id,
                        key = it.key,
                        startAddress = it.startAddress,
                        finishAddress = it.finishAddress,
                        startDateTime = it.startDateTime,
                        finishDateTime = it.finishDateTime,
                        route = it.route,
                        distance = it.distance,
                        averageSpeed = it.averageSpeed,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    )
                }
                rideDao.upsertAllRides(rideEntities)
            }
            MediatorResult.Success(endOfPaginationReached = rideDtos.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
