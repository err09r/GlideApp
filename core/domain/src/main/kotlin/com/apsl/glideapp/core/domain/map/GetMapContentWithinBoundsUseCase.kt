package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class GetMapContentWithinBoundsUseCase @Inject constructor(
    private val mapRepository: MapRepository,
    private val zoneRepository: ZoneRepository
) {

    suspend operator fun invoke(bounds: CoordinatesBounds) = runCatching {
        // Determine whether send a request or skip
        val shouldLoadContent = zoneRepository.getAllZones().firstOrNull()?.any { zone ->
            zone.coordinates.any { bounds.contains(it) }
        } ?: true

        if (shouldLoadContent) {
            mapRepository.getMapContentWithinBounds(bounds)
        }
    }
}
