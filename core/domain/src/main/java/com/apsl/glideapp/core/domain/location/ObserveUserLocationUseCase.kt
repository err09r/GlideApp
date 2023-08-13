package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.util.asResult
import javax.inject.Inject

class ObserveUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke() = locationRepository.userLocation.asResult()
}
