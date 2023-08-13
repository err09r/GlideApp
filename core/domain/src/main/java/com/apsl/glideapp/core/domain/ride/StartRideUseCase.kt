package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.domain.location.AddressDecoder
import javax.inject.Inject
import kotlinx.datetime.LocalDateTime

class StartRideUseCase @Inject constructor(
    private val rideRepository: RideRepository,
    private val addressDecoder: AddressDecoder
) {

    suspend operator fun invoke(vehicleId: String, userCoordinates: Coordinates) = runCatching {
        val address = addressDecoder.decodeFromCoordinates(userCoordinates)
        rideRepository.updateRideState(
            RideAction.Start(
                vehicleId = vehicleId,
                coordinates = userCoordinates,
                address = address,
                dateTime = LocalDateTime.now()
            )
        )
    }
}
