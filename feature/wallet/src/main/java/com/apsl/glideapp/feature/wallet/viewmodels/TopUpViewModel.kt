package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.core.domain.transaction.CreateTransactionUseCase
import com.apsl.glideapp.core.domain.transaction.GetAllPaymentMethodsUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.wallet.models.PaymentMethodUiModel
import com.apsl.glideapp.feature.wallet.models.toPaymentMethods
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
            //TODO: Handle String to Double parsing
            _actions.emit(PaymentAction.PaymentProcessingStarted)
            val amount = uiState.value.amountTextFieldValue?.replace(',', '.')?.toDouble() ?: 0.0
            Timber.d("Parsed amount: $amount")
            createTransactionUseCase(type = TransactionType.TopUp, amount = amount)
                .onSuccess {
                    _uiState.update { it.copy(amountTextFieldValue = "0,0") }
                    _actions.emit(PaymentAction.PaymentProcessingCompleted)
                }
                .onFailure {
                    Timber.d(it.message)
                    _actions.emit(PaymentAction.PaymentProcessingFailed)
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
