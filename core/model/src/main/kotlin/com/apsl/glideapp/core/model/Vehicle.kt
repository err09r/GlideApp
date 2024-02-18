package com.apsl.glideapp.core.model

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.VehicleStatus
import com.apsl.glideapp.common.models.VehicleType

data class Vehicle(
    val id: String,
    val code: String,
    val type: VehicleType,
    val status: VehicleStatus,
    val coordinates: Coordinates,
    val rangeKilometers: String,
    val unlockingFee: String,
    val farePerMinute: String,
    val batteryCharge: String,
    val batteryState: BatteryState
)
