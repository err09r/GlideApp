package com.apsl.glideapp.feature.wallet.viewmodels

sealed interface VoucherAction {
    data object VoucherProcessingStarted : VoucherAction
    data object VoucherProcessingCompleted : VoucherAction
    data class VoucherProcessingError(val message: String) : VoucherAction
}
