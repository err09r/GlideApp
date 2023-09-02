package com.apsl.glideapp.core.domain.location

import javax.inject.Inject

class StopReceivingLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() = runCatching {
        locationRepository.stopReceivingLocationUpdates()
    }
}
