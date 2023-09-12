package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.CoordinatesBounds
import javax.inject.Inject

class GetMapContentWithinBoundsUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {

    suspend operator fun invoke(bounds: CoordinatesBounds) = runCatching {
        mapRepository.getMapContentWithinBounds(bounds)
    }
}
