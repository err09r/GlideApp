package com.apsl.glideapp.core.domain.ride

import com.apsl.glideapp.common.models.Coordinates
import kotlinx.datetime.LocalDateTime

data class Ride(
    val id: String,
    val startAddress: String?,
    val finishAddress: String?,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime,
    val route: List<Coordinates>,
    val distance: Double,
    val averageSpeed: Double
)
