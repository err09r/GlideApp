package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.RideAction
import javax.inject.Inject

class UpdateRideRouteUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    suspend operator fun invoke(rideId: String, userCoordinates: Coordinates) = runCatching {
        rideRepository.updateRideState(
            action = RideAction.UpdateRoute(rideId = rideId, coordinates = userCoordinates)
        )
    }
}
