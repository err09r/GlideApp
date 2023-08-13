package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.transaction.CreateVoucherTransactionUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
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
class VoucherViewModel @Inject constructor(
    private val createVoucherTransactionUseCase: CreateVoucherTransactionUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(VoucherUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<VoucherAction>(replay = 1)
    val actions = _actions.asSharedFlow()

    fun setCodeTextFieldValue(value: String?) {
        _uiState.update { it.copy(codeTextFieldValue = value?.uppercase()) }
    }

    fun activateCode() {
        viewModelScope.launch {
            val voucherCode = uiState.value.codeTextFieldValue
            if (voucherCode != null) {
                _actions.emit(VoucherAction.VoucherProcessingStarted)
                createVoucherTransactionUseCase(voucherCode = voucherCode)
                    .onSuccess {
                        _uiState.update { it.copy(codeTextFieldValue = null) }
                        _actions.emit(VoucherAction.VoucherProcessingCompleted)
                    }
                    .onFailure { throwable ->
                        Timber.d(throwable)
                        _actions.emit(VoucherAction.VoucherProcessingError("Invalid voucher code"))
                    }
            }
        }
    }
}
