package com.apsl.glideapp.feature.home.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
sealed interface HomeAction {
    data object LogOut : HomeAction
    data class StartRide(val rideId: String) : HomeAction
    data class RestartUserLocation(val rideId: String) : HomeAction
    data object FinishRide : HomeAction
    data class Toast(val message: String) : HomeAction
    data object OpenLocationSettingsDialog : HomeAction
    data object RequestLocationPermissions : HomeAction
}
