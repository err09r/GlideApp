package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.apsl.glideapp.core.domain.transaction.GetUserTransactionsPaginatedUseCase
import com.apsl.glideapp.core.domain.transaction.Transaction
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel
import com.apsl.glideapp.feature.wallet.models.toTransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    getUserTransactionsPaginatedUseCase: GetUserTransactionsPaginatedUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AllTransactionsUiState())
    val uiState = _uiState.asStateFlow()

    val pagingData = getUserTransactionsPaginatedUseCase()
        .map { pagingData ->
            pagingData.map(Transaction::toTransactionUiModel)
        }
        .cachedIn(viewModelScope)

    fun onNewPagerLoadState(pagingItems: LazyPagingItems<TransactionUiModel>) {
        val loadState = pagingItems.loadState.refresh
        when {
            loadState is LoadState.Loading -> {
                _uiState.update { it.copy(isLoading = true, isRefreshing = true) }
            }

            loadState is LoadState.Error && pagingItems.itemCount == 0 -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = AllTransactionsUiError(loadState.error)
                    )
                }
            }

            pagingItems.itemCount == 0 -> {
                _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false, transactions = pagingItems)
                }
            }
        }
    }

    fun refresh() {
        uiState.value.transactions?.refresh()
    }
}
