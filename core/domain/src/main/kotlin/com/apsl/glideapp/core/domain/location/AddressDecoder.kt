package com.apsl.glideapp.core.domain.location

import com.apsl.glideapp.common.models.Coordinates

interface AddressDecoder {
    suspend fun decodeFromCoordinates(coordinates: Coordinates): String?
}
