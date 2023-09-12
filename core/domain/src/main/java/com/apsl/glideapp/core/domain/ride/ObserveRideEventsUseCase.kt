package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.common.util.asResult
import javax.inject.Inject

class ObserveRideEventsUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    operator fun invoke() = rideRepository.rideEvents.asResult()
}
