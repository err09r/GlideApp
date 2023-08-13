package com.apsl.glideapp.core.domain.home

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.VehicleStatus

data class Vehicle(
    val id: String,
    val code: Int,
    val batteryCharge: Int,
    val status: VehicleStatus,
    val coordinates: Coordinates
)
