package com.apsl.glideapp.feature.wallet.wallet

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.transaction.GetUserTransactionsUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.wallet.common.TransactionUiModel
import com.apsl.glideapp.feature.wallet.common.toTransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Immutable
data class WalletUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val userBalance: String = "0,00 zł",
    val isRentalAvailable: Boolean = true,
    val recentTransactions: List<TransactionUiModel> = emptyList(),
    val error: WalletUiError? = null
)

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserTransactionsUseCase: GetUserTransactionsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState = _uiState.asStateFlow()

    private var userTransactions: List<TransactionUiModel> = emptyList()

    fun getUserBalance() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess { user ->
                    if (user == null) {
                        return@onSuccess
                    }
                    _uiState.update { state ->
                        state.copy(
                            userBalance = "${user.balance.format(2)} zł",
                            isRentalAvailable = user.balance > 0
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.d(throwable.message)
                    if (userTransactions.isNotEmpty()) {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
        }
    }

    fun getUserTransactions() {
        viewModelScope.launch {
            showLoading()
            getUserTransactionsUseCase(limit = 4)
                .onSuccess { transactions ->
                    _uiState.update { state ->
                        val lastTransactions = transactions
                            .take(4)
                            .map { it.toTransactionUiModel() }

                        userTransactions = lastTransactions

                        state.copy(
                            isLoading = false,
                            recentTransactions = lastTransactions
                        )
                    }
                }
                .onFailure { Timber.d(it.message) }
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
                    Timber.d(throwable.message)
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
