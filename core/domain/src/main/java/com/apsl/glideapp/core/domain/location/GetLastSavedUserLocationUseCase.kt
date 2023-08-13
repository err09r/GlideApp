package com.apsl.glideapp.core.domain.location

import javax.inject.Inject

class GetLastSavedUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() = runCatching {
        locationRepository.getLastSavedUserLocation()
    }
}
