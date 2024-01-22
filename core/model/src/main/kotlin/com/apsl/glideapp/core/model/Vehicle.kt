package com.apsl.glideapp.core.model

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.VehicleStatus
import com.apsl.glideapp.common.models.VehicleType

data class Vehicle(
    val id: String,
    val code: Int,
    val batteryCharge: Int,
    val type: VehicleType,
    val status: VehicleStatus,
    val coordinates: Coordinates,
    val unlockingFee: Double,
    val farePerMinute: Double
)
