package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
sealed interface HomeAction {
    data class RideStarted(val rideId: String) : HomeAction
    object RideFinished : HomeAction
    data class Toast(val message: String) : HomeAction
}
