package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.Vehicle
import kotlinx.datetime.LocalDateTime
import kotlin.math.roundToInt

@Immutable
data class RideState(
    val startDateTime: LocalDateTime,
    val vehicle: VehicleUiModel,
    val distance: String = "0,0",
    val isPaused: Boolean = false
) {
    val isActive get() = !isPaused
}

@Immutable
data class VehicleUiModel(
    val id: String,
    val code: String,
    val range: Int,
    val unlockingFee: Double,
    val farePerMinute: Double,
    val batteryCharge: Int,
    val batteryState: BatteryState
)

enum class BatteryState {
    Undefined, Low, Medium, Full
}

fun Vehicle.toVehicleUiModel(): VehicleUiModel {
    return VehicleUiModel(
        id = this.id,
        code = this.code.toString().padStart(4, '0'),
        range = (this.batteryCharge * 0.4).roundToInt(),
        unlockingFee = this.unlockingFee,
        farePerMinute = this.farePerMinute,
        batteryCharge = batteryCharge,
        batteryState = when (this.batteryCharge) {
            in 0..34 -> BatteryState.Low
            in 35..84 -> BatteryState.Medium
            in 85..100 -> BatteryState.Full
            else -> BatteryState.Undefined
        }
    )
}
