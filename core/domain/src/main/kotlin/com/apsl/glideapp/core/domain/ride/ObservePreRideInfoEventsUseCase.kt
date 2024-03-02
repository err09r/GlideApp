package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.core.model.PreRideInfoEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObservePreRideInfoEventsUseCase @Inject constructor(
    private val preRideInfoEventRepository: PreRideInfoEventRepository
) {

    operator fun invoke(): Flow<PreRideInfoEvent> = preRideInfoEventRepository.preRideInfoEvents
}
