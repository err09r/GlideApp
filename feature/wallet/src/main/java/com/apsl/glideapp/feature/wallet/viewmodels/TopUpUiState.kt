package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.feature.wallet.models.PaymentMethodUiModel

@Immutable
data class TopUpUiState(
    val isLoading: Boolean = false,
    val paymentMethods: List<PaymentMethodUiModel> = emptyList(),
    val selectedPaymentMethodIndex: Int = 0,
    val amountTextFieldValue: String? = null,
    val error: TopUpUiError? = null
)
