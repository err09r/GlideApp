package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.models.Coordinates
import javax.inject.Inject

class DecodeAddressUseCase @Inject constructor(
    private val addressDecoder: AddressDecoder
) {

    suspend operator fun invoke(coordinates: Coordinates) = runCatching {
        addressDecoder.decodeFromCoordinates(coordinates)
    }
}
