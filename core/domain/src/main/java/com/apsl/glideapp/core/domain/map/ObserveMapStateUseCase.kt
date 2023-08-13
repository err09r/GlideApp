package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.util.asResult
import javax.inject.Inject

class ObserveMapStateUseCase @Inject constructor(private val mapRepository: MapRepository) {

    operator fun invoke() = mapRepository.getMapStateUpdates().asResult()
}
