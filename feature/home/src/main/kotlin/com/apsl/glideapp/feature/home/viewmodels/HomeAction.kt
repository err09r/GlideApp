@file:Suppress("Unused")

package com.apsl.glideapp.feature.home.viewmodels

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface HomeAction {
    data object LogOut : HomeAction
    data class StartRide(val rideId: String, val startDateTime: String) : HomeAction
    data class RestartUserLocation(val rideId: String, val startDateTime: String) : HomeAction
    data object FinishRide : HomeAction
    data class Toast(@StringRes val textResId: Int) : HomeAction
    data object OpenLocationSettingsDialog : HomeAction
    data object RequestLocationPermissions : HomeAction
    data object OpenTopUpScreen : HomeAction
}
