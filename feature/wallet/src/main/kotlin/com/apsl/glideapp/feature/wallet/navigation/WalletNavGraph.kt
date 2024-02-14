package com.apsl.glideapp.feature.wallet.navigation

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.apsl.glideapp.core.ui.navigation.AppNavGraph
import com.apsl.glideapp.core.ui.navigation.Dialog
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.wallet.common.PaymentDialog
import com.apsl.glideapp.feature.wallet.topup.TopUpScreen
import com.apsl.glideapp.feature.wallet.topup.TopUpSuccessDialog
import com.apsl.glideapp.feature.wallet.transactions.AllTransactionsScreen
import com.apsl.glideapp.feature.wallet.voucher.VoucherActivatedDialog
import com.apsl.glideapp.feature.wallet.voucher.VoucherScreen
import com.apsl.glideapp.feature.wallet.wallet.WalletScreen

fun NavGraphBuilder.walletGraph(navController: NavController) {
    navigation(startDestination = Screen.Wallet.Root.route, route = AppNavGraph.Wallet.route) {

        composable(route = Screen.Wallet.Root.route) {
            WalletScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTransactions = { navController.navigate(Screen.Wallet.AllTransactions.route) },
                onNavigateToRedeemVoucher = { navController.navigate(Screen.Wallet.RedeemVoucher.route) },
                onNavigateToTopUp = { navController.navigate(Screen.Wallet.TopUp.route) }
            )
        }

        composable(route = Screen.Wallet.AllTransactions.route) {
            AllTransactionsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Wallet.TopUp.route) {
            TopUpScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPayment = { navController.navigate(Dialog.Wallet.Payment.route) },
                onNavigateToTopUpSuccess = {
                    navController.navigate(Dialog.Wallet.TopUpSuccess.route) {
                        popUpTo(Screen.Wallet.TopUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Wallet.RedeemVoucher.route) {
            VoucherScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPayment = { navController.navigate(Dialog.Wallet.Payment.route) },
                onNavigateToVoucherActivated = {
                    navController.navigate(Dialog.Wallet.VoucherActivated.route) {
                        popUpTo(Screen.Wallet.RedeemVoucher.route) { inclusive = true }
                    }
                }
            )
        }

        dialog(
            route = Dialog.Wallet.Payment.route,
            dialogProperties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            PaymentDialog()
        }

        dialog(
            route = Dialog.Wallet.TopUpSuccess.route,
//            arguments = listOf(navArgument("amount") { type = NavType.StringType })
        ) {
//            val amount = backStackEntry.arguments?.getString("amount")
            TopUpSuccessDialog(onDismiss = { navController.popBackStack() })//amount = requireNotNull(amount))
        }

        dialog(route = Dialog.Wallet.VoucherActivated.route) {
            VoucherActivatedDialog(onDismiss = { navController.popBackStack() })
        }
    }
}
