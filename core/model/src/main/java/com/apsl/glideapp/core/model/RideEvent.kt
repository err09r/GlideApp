package com.apsl.glideapp.core.model

import com.apsl.glideapp.common.models.Coordinates
import kotlinx.datetime.LocalDateTime

sealed interface RideEvent {
    data class Started(val rideId: String, val vehicle: Vehicle, val dateTime: LocalDateTime) :
        RideEvent

    data class RouteUpdated(val currentRoute: List<Coordinates>) : RideEvent
    data object Finished : RideEvent
    sealed class Error(val message: String?) : RideEvent {
        class UserInsideNoParkingZone(message: String?) : Error(message)
    }
}
