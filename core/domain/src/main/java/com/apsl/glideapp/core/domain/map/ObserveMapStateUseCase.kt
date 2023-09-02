package com.apsl.glideapp.core.domain.map

import javax.inject.Inject

class ObserveMapStateUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {

    operator fun invoke() = mapRepository.mapStateUpdates//.asResult()
}
