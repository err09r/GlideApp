package com.apsl.glideapp.core.model

import com.apsl.glideapp.common.models.Route
import kotlinx.datetime.LocalDateTime

data class Ride(
    val id: String,
    val startAddress: String?,
    val finishAddress: String?,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime,
    val route: Route,
    val averageSpeedKmh: Double
)
