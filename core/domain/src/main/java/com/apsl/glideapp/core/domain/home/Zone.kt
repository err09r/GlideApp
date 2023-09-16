package com.apsl.glideapp.core.domain.home

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.ZoneType

data class Zone(
    val id: String,
    val code: Int,
    val title: String,
    val type: ZoneType,
    val coordinates: List<Coordinates>
)
