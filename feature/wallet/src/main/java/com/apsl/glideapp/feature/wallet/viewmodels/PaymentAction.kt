package com.apsl.glideapp.feature.wallet.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
sealed interface PaymentAction {
    data object PaymentProcessingStarted : PaymentAction
    data object PaymentProcessingCompleted : PaymentAction
    data object PaymentProcessingFailed : PaymentAction
}
