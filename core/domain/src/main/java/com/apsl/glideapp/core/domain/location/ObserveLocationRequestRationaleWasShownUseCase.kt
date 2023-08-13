package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.util.asResult
import javax.inject.Inject

class ObserveLocationRequestRationaleWasShownUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke() = locationRepository.wasLocationRequestRationaleShown.asResult()
}
