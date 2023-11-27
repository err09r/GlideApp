package com.apsl.glideapp.feature.wallet.voucher

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface VoucherAction {
    data object VoucherProcessingStarted : VoucherAction
    data object VoucherProcessingCompleted : VoucherAction
    data class VoucherActivationError(val message: String) : VoucherAction
}
