package com.apsl.glideapp.core.domain.ride

import androidx.paging.PagingData
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.model.RideEvent
import kotlinx.coroutines.flow.Flow

interface RideRepository {
    val isRideModeActive: Flow<Boolean>
    val rideEvents: Flow<RideEvent>
    suspend fun updateRideState(action: RideAction)
    fun getUserRidesPaginated(): Flow<PagingData<Ride>>
    suspend fun getRideById(id: String): Ride
}
