package com.apsl.glideapp.core.domain.ride

import androidx.paging.PagingData
import com.apsl.glideapp.common.models.RideAction
import kotlinx.coroutines.flow.Flow

interface RideRepository {
    val rideEvents: Flow<RideEvent>
    suspend fun updateRideState(action: RideAction)
    fun getUserRidesPaginated(): Flow<PagingData<Ride>>
    suspend fun getRideById(id: String): Ride
}
