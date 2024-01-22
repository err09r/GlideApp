package com.apsl.glideapp.feature.wallet.topup

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.core.domain.transaction.CreateTransactionUseCase
import com.apsl.glideapp.core.domain.transaction.GetAllPaymentMethodsUseCase
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

@Immutable
data class TopUpUiState(
    val isLoading: Boolean = false,
    val paymentMethods: PaymentMethods = PaymentMethods(emptyList()),
    val selectedPaymentMethodIndex: Int = 0,
    val amountTextFieldValue: String? = null,
    val error: TopUpUiError? = null
)

@Immutable
@JvmInline
value class PaymentMethods(val value: List<PaymentMethodUiModel>)

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase
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
            //TODO: Handle String to Double parsing (move to a UseCase)
            _actions.send(TopUpAction.PaymentProcessingStarted)
            val amount = uiState.value.amountTextFieldValue?.replace(',', '.')?.toDouble() ?: 0.0
            Timber.d("Parsed amount: $amount")
            createTransactionUseCase(type = TransactionType.TopUp, amount = amount)
                .onSuccess {
                    _uiState.update { it.copy(amountTextFieldValue = "0,0") }
                    _actions.send(TopUpAction.PaymentProcessingCompleted)
                }
                .onFailure {
                    Timber.d(it.message)
                    _actions.send(TopUpAction.PaymentProcessingFailed)
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
