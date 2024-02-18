package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.BatteryState
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.util.android.DistanceFormatter
import kotlinx.datetime.LocalDateTime

@Immutable
data class RideState(
    val startDateTime: LocalDateTime,
    val vehicle: VehicleUiModel,
    val distanceKilometers: String = DistanceFormatter.default(),
    val isPaused: Boolean = false
) {
    val isActive get() = !isPaused
}

@Immutable
data class VehicleUiModel(
    val id: String,
    val code: String,
    val rangeKilometers: String,
    val unlockingFee: String,
    val farePerMinute: String,
    val batteryCharge: String,
    val batteryState: BatteryState
)

fun Vehicle.toVehicleUiModel(): VehicleUiModel {
    return VehicleUiModel(
        id = id,
        code = code,
        rangeKilometers = rangeKilometers,
        unlockingFee = unlockingFee,
        farePerMinute = farePerMinute,
        batteryCharge = batteryCharge,
        batteryState = batteryState
    )
}
