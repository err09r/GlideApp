@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.navigation

sealed class Dialog(val route: String) {

    object Home {
        data object LocationPermission : Screen("locationPermission")
        data object LocationRationale : Screen("locationRationale")
        data object NotificationPermission : Screen("notificationPermission")
    }

    object Wallet {
        data object Payment : Dialog("payment")
        data object TopUpSuccess : Dialog("topUpSuccess")
        data object VoucherActivated : Dialog("voucherActivated")
    }
}
