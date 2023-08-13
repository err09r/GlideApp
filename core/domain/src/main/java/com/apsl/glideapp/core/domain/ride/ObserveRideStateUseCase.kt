package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.common.util.asResult
import javax.inject.Inject

class ObserveRideStateUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    operator fun invoke() = rideRepository.receiveRideStateUpdates().asResult()
}
