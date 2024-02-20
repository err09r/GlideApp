package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.core.model.PreRideInfoEvent
import javax.inject.Inject

class SendPreRideInfoEventUseCase @Inject constructor(
    private val preRideInfoEventRepository: PreRideInfoEventRepository
) {

    suspend operator fun invoke(event: PreRideInfoEvent) = runCatching {
        preRideInfoEventRepository.send(event)
    }
}
