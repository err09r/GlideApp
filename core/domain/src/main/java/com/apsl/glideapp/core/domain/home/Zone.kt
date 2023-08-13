package com.apsl.glideapp.core.domain.home

import com.apsl.glideapp.common.models.Coordinates

data class Zone(
    val id: String,
    val code: Int,
    val title: String,
    val coordinates: List<Coordinates>
)
