package com.apsl.glideapp.core.domain.ride

import javax.inject.Inject

// Used ONLY for Paging3 workaround
class GetAllRideCoordinatesUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    operator fun invoke() = rideRepository.getAllRideCoordinates()
}

// Used ONLY for Paging3 workaround
data class RideCoordinates(
    val rideId: String,
    val latitude: Double,
    val longitude: Double
)
