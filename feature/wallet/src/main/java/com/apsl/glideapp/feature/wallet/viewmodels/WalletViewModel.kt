package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.transaction.GetUserTransactionsUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.wallet.models.toTransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserTransactionsUseCase: GetUserTransactionsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState = _uiState.asStateFlow()

    fun getUserBalance() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess { user ->
                    _uiState.update { state ->
                        state.copy(
                            userBalance = "${user.balance.format(2)} PLN",
                            isRentalAvailable = user.balance > 0
                        )
                    }
                }
                .onFailure(Timber::d)
        }
    }

    fun getUserTransactions() {
        viewModelScope.launch {
            showLoading()
            getUserTransactionsUseCase(limit = 4)
                .onSuccess { transactions ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            recentTransactions = transactions
                                .take(4)
                                .map { it.toTransactionUiModel() }
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.d(throwable)
                    _uiState.update { it.copy(isLoading = false, error = WalletUiError(throwable)) }
                }
        }
    }

    fun refresh() {
        showRefresh()
        getUserBalance()
        viewModelScope.launch {
            getUserTransactionsUseCase(limit = 4)
                .onSuccess { transactions ->
                    _uiState.update { state ->
                        state.copy(
                            isRefreshing = false,
                            recentTransactions = transactions
                                .take(4)
                                .map { it.toTransactionUiModel() }
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.d(throwable)
                    _uiState.update {
                        it.copy(isRefreshing = false, error = WalletUiError(throwable))
                    }
                }
        }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun showRefresh() {
        _uiState.update { it.copy(isRefreshing = true) }
    }
}
