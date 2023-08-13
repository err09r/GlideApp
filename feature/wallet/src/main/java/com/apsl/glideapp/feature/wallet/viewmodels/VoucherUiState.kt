package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
data class VoucherUiState(
    val isLoading: Boolean = false,
    val codeTextFieldValue: String? = null,
    val error: Exception? = null
)
