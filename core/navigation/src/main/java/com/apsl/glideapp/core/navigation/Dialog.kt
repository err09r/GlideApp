package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class Dialog(val route: String) {
    object Payment : Dialog("payment")
    object TopUpSuccess : Dialog("topUpSuccess")
}
