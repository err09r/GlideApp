package com.apsl.glideapp.core.domain.ride

import javax.inject.Inject

class GetUserRidesPaginatedUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    operator fun invoke() = rideRepository.getUserRidesPaginated()
}
