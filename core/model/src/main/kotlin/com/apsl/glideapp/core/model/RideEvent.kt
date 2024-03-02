package com.apsl.glideapp.core.model

import com.apsl.glideapp.common.models.Route
import kotlinx.datetime.LocalDateTime

sealed interface RideEvent {
    data class Started(
        val rideId: String,
        val vehicle: Vehicle,
        val dateTime: LocalDateTime
    ) : RideEvent

    data class RouteUpdated(val currentRoute: Route) : RideEvent
    data class Finished(val distance: Double, val averageSpeed: Double) : RideEvent
    sealed class Error(val message: String?) : RideEvent {
        class UserInsideNoParkingZone(message: String?) : Error(message)
        class UserTooFarFromVehicle(message: String?) : Error(message)
        class NotEnoughFunds(message: String?) : Error(message)
    }
}
