package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.core.model.PreRideInfoEvent
import kotlinx.coroutines.flow.Flow

interface PreRideInfoEventRepository {
    val preRideInfoEvents: Flow<PreRideInfoEvent>
    suspend fun send(event: PreRideInfoEvent)
}
