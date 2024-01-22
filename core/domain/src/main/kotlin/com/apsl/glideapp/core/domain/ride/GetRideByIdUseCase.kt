package com.apsl.glideapp.core.domain.ride

import javax.inject.Inject

class GetRideByIdUseCase @Inject constructor(private val rideRepository: RideRepository) {

    suspend operator fun invoke(id: String) = runCatching {
        rideRepository.getRideById(id)
    }
}
