package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.models.Coordinates
import javax.inject.Inject

class SaveLastUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(location: Coordinates) = runCatching {
        locationRepository.saveLastUserLocation(location)
    }
}
