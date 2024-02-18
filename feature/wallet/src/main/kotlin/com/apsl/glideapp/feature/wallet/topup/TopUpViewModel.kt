package com.apsl.glideapp.feature.wallet.topup

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.core.domain.transaction.CreateTransactionUseCase
import com.apsl.glideapp.core.domain.transaction.GetAllPaymentMethodsUseCase
import com.apsl.glideapp.core.domain.transaction.ParseUserTransactionAmountUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class TopUpUiState(
    val isLoading: Boolean = false,
    val paymentMethods: PaymentMethods = PaymentMethods(emptyList()),
    val selectedPaymentMethodIndex: Int = 0,
    val amountTextFieldValue: String? = null,
    @StringRes val invalidAmountErrorResId: Int? = null
) {
    val isActionButtonActive: Boolean
        get() = !amountTextFieldValue.isNullOrBlank() &&
                amountTextFieldValue.contains(validationRegex)
}

private val validationRegex by lazy { Regex("[1-9]") }

@Immutable
@JvmInline
value class PaymentMethods(val value: List<PaymentMethodUiModel>)

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val parseUserTransactionAmountUseCase: ParseUserTransactionAmountUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TopUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<TopUpAction>()
    val actions = _actions.receiveAsFlow()

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
                        paymentMethods = paymentMethods.toPaymentMethods()
                    )
                }
            }
            .onFailure { Timber.d(it.message) }
    }

    fun setSelectedPaymentMethodIndex(index: Int) {
        _uiState.update {
            it.copy(selectedPaymentMethodIndex = index.coerceIn(it.paymentMethods.value.indices))
        }
    }

    fun startPaymentProcessing() {
        viewModelScope.launch {

            parseUserTransactionAmountUseCase(value = uiState.value.amountTextFieldValue)
                .onSuccess { parsedAmount ->
                    _actions.send(TopUpAction.PaymentProcessingStarted)
                    createTransactionUseCase(type = TransactionType.TopUp, amount = parsedAmount)
                        .onSuccess {
                            _uiState.update { it.copy(amountTextFieldValue = null) }
                            _actions.send(TopUpAction.PaymentProcessingCompleted)
                        }
                        .onFailure { throwable ->
                            Timber.d(throwable.message)
                            _actions.send(TopUpAction.PaymentProcessingFailed)
                        }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            amountTextFieldValue = null,
                            invalidAmountErrorResId = CoreR.string.invalid_amount_format
                        )
                    }
                }
        }
    }

    fun updateAmountTextFieldValue(value: String?) {
        _uiState.update { it.copy(amountTextFieldValue = value) }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }
}
