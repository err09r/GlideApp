package com.apsl.glideapp.feature.wallet.topup

import androidx.compose.runtime.Immutable

@Immutable
sealed interface TopUpAction {
    data object PaymentProcessingStarted : TopUpAction
    data object PaymentProcessingCompleted : TopUpAction
    data object PaymentProcessingFailed : TopUpAction
}
