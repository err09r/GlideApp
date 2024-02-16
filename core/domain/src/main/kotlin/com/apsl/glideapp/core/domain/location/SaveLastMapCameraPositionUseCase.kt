package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.core.model.MapCameraPosition
import javax.inject.Inject

class SaveLastMapCameraPositionUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(position: MapCameraPosition) = runCatching {
        locationRepository.saveLastMapCameraPosition(position)
    }
}
