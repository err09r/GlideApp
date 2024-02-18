package com.apsl.glideapp.feature.home.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.BatteryState
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.util.maps.toLatLng
import com.google.android.gms.maps.model.LatLng

@Immutable
data class SelectedVehicleUiModel(
    val id: String,
    val code: String,
    val rangeKilometers: String,
    val unlockingFee: String,
    val farePerMinute: String,
    val coordinates: LatLng,
    val batteryCharge: String,
    val batteryState: BatteryState
)

fun Vehicle.toSelectedVehicleUiModel(): SelectedVehicleUiModel {
    return SelectedVehicleUiModel(
        id = this.id,
        code = this.code,
        rangeKilometers = this.rangeKilometers,
        unlockingFee = this.unlockingFee,
        farePerMinute = this.farePerMinute,
        coordinates = this.coordinates.toLatLng(),
        batteryCharge = this.batteryCharge,
        batteryState = this.batteryState
    )
}
