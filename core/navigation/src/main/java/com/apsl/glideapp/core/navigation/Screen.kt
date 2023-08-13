package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class Screen(val route: String) {
    object Home {
        object Root : Screen("home")
        object LocationPermission : Screen("locationPermission")
    }

    object Rides {
        object Root : Screen("rides")
        data class RideDetails(val id: String) : Screen("ride/$id") {
            companion object {
                const val route = "ride/{id}"
            }
        }
    }

    object Wallet {
        object Root : Screen("wallet")
        object AllTransactions : Screen("transactions")
        object TopUp : Screen("topUp")
        object RedeemVoucher : Screen("redeemVoucher")
    }

    object Auth {
        object Login : Screen("login")
        object Register : Screen("register")
    }
}
