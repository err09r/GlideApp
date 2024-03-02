package com.apsl.glideapp.feature.wallet.voucher

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.transaction.CreateVoucherTransactionUseCase
import com.apsl.glideapp.core.domain.transaction.TransactionException
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
data class VoucherUiState(val codeTextFieldValue: String? = null) {
    val isActionButtonActive: Boolean get() = !codeTextFieldValue.isNullOrBlank()
}

@HiltViewModel
class VoucherViewModel @Inject constructor(
    private val createVoucherTransactionUseCase: CreateVoucherTransactionUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(VoucherUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<VoucherAction>()
    val actions = _actions.receiveAsFlow()

    fun updateCodeTextFieldValue(value: String?) {
        _uiState.update { it.copy(codeTextFieldValue = value?.uppercase()) }
    }

    fun activateCode() {
        viewModelScope.launch {
            val voucherCode = uiState.value.codeTextFieldValue
            if (voucherCode != null) {
                _actions.send(VoucherAction.VoucherProcessingStarted)
                createVoucherTransactionUseCase(voucherCode = voucherCode)
                    .onSuccess {
                        _uiState.update { it.copy(codeTextFieldValue = null) }
                        _actions.send(VoucherAction.VoucherProcessingCompleted)
                    }
                    .onFailure { throwable ->
                        Timber.d(throwable.message)
                        var textResId: Int = CoreR.string.error_connection
                        if (throwable is TransactionException.InvalidVoucherCodeException) {
                            textResId = CoreR.string.error_voucher_invalid_code
                            updateCodeTextFieldValue(null)
                        }
                        _actions.send(VoucherAction.VoucherActivationError(textResId))
                    }
            }
        }
    }
}
