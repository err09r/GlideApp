package com.apsl.glideapp.feature.wallet.voucher

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface VoucherAction {
    data object VoucherProcessingStarted : VoucherAction
    data object VoucherProcessingCompleted : VoucherAction
    data class VoucherActivationError(@StringRes val textResId: Int) : VoucherAction
}
