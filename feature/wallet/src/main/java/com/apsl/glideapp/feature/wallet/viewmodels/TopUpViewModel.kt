package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.core.domain.transaction.CreateTransactionUseCase
import com.apsl.glideapp.core.domain.transaction.GetAllPaymentMethodsUseCase
import com.apsl.glideapp.core.domain.transaction.PaymentMethod
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.wallet.models.toPaymentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TopUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<PaymentAction>(replay = 1)
    val actions = _actions.asSharedFlow()

    init {
        getAllPaymentMethods()
    }

    private fun getAllPaymentMethods() {
        showLoading()
        getAllPaymentMethodsUseCase()
            .onSuccess { paymentMethods ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentMethods = paymentMethods.map(PaymentMethod::toPaymentUiModel)
                    )
                }
            }
            .onFailure(Timber::d)
    }

    fun setSelectedPaymentMethodIndex(index: Int) {
        _uiState.update { state ->
            state.copy(
                selectedPaymentMethodIndex = index.coerceIn(
                    minimumValue = 0,
                    maximumValue = state.paymentMethods.size
                )
            )
        }
    }

    fun startPaymentProcessing() {
        viewModelScope.launch {
            //TODO: Handle String to Double parsing
            _actions.emit(PaymentAction.PaymentProcessingStarted)
            createTransactionUseCase(
                type = TransactionType.TopUp,
                amount = uiState.value.amountTextFieldValue?.toDouble() ?: 0.0
            )
                .onSuccess {
                    _uiState.update { it.copy(amountTextFieldValue = "0.0") }
                    _actions.emit(PaymentAction.PaymentProcessingCompleted)
                }
                .onFailure(Timber::d)
        }
    }

    fun handleFocusEvent(isFocused: Boolean) {
        _uiState.update { state ->
            state.copy(
                amountTextFieldValue = when {
                    !isFocused && state.amountTextFieldValue?.isBlank() == true -> ""
                    else -> state.amountTextFieldValue
                }
            )
        }
    }

    fun setAmountTextFieldValue(value: String?) {
        _uiState.update { it.copy(amountTextFieldValue = value) }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }
}
