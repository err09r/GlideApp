package com.apsl.glideapp.core.domain.ride

import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class IsRideModeActiveUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {
    suspend operator fun invoke(): Boolean {
        return rideRepository.isRideModeActive.firstOrNull() ?: false
    }
}
