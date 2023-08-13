package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel

@Immutable
data class WalletUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val userBalance: String = "0.00 PLN",
    val isRentalAvailable: Boolean = true,
    val recentTransactions: List<TransactionUiModel> = emptyList(),
    val error: WalletUiError? = null
)
