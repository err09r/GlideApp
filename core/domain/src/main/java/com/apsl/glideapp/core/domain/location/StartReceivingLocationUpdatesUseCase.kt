package com.apsl.glideapp.core.domain.location

import javax.inject.Inject

class StartReceivingLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() = runCatching {
        locationRepository.startReceivingLocationUpdates()
    }
}
