package com.apsl.glideapp.core.domain.location

import javax.inject.Inject

class IsGpsEnabledUseCase @Inject constructor(
    private val locationClient: LocationClient
) {

    operator fun invoke() = locationClient.isGpsEnabled
}
