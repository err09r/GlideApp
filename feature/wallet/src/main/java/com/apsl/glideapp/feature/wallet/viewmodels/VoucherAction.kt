package com.apsl.glideapp.feature.wallet.viewmodels

sealed interface VoucherAction {
    object VoucherProcessingStarted : VoucherAction
    object VoucherProcessingCompleted : VoucherAction
    data class VoucherProcessingError(val message: String) : VoucherAction
}
