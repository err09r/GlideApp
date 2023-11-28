package com.apsl.glideapp.core.domain.ride

import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class IsRideModeActiveUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    suspend operator fun invoke(): Boolean {
        return rideRepository.isRideModeActive.firstOrNull() ?: false
    }
}
