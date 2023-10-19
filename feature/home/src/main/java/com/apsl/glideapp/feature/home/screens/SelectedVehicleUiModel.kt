package com.apsl.glideapp.feature.home.screens

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.util.maps.toLatLng
import com.google.android.gms.maps.model.LatLng
import kotlin.math.roundToInt

@Immutable
data class SelectedVehicleUiModel(
    val id: String,
    val code: String,
    val range: Int,
    val unlockingFee: Double,
    val farePerMinute: Double,
    val coordinates: LatLng,
    val batteryState: BatteryState
)

enum class BatteryState {
    Undefined, Low, Medium, Full
}

fun Vehicle.toSelectedVehicleUiModel(): SelectedVehicleUiModel {
    return SelectedVehicleUiModel(
        id = this.id,
        code = this.code.toString().padStart(4, '0'),
        range = (this.batteryCharge * 0.4).roundToInt(),
        unlockingFee = this.unlockingFee,
        farePerMinute = this.farePerMinute,
        coordinates = this.coordinates.toLatLng(),
        batteryState = when (this.batteryCharge) {
            in 0..34 -> BatteryState.Low
            in 35..84 -> BatteryState.Medium
            in 85..100 -> BatteryState.Full
            else -> BatteryState.Undefined
        }
    )
}
