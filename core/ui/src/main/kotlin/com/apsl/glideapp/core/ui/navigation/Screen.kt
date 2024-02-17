@file:Suppress("Unused", "ConstPropertyName")

package com.apsl.glideapp.core.ui.navigation

sealed class Screen(val route: String) {
    object Home {
        data object Root : Screen("home")
    }

    object Rides {
        data object Root : Screen("rides")
        data class RideDetails(val id: String) : Screen("ride/$id") {
            companion object {
                const val route = "ride/{id}"
            }
        }
    }

    object Wallet {
        data object Root : Screen("wallet")
        data object AllTransactions : Screen("transactions")
        data object TopUp : Screen("topUp")
        data object RedeemVoucher : Screen("redeemVoucher")
    }

    object Auth {
        data object Login : Screen("login")
        data object Register : Screen("register")
    }
}
