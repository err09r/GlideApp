package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel

@Immutable
data class AllTransactionsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val transactions: LazyPagingItems<TransactionUiModel>? = null,
    val error: AllTransactionsUiError? = null
)
