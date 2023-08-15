package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class Dialog(val route: String) {
    data object Payment : Dialog("payment")
    data object TopUpSuccess : Dialog("topUpSuccess")
}
