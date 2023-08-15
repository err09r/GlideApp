package com.apsl.glideapp.feature.wallet.viewmodels

sealed interface PaymentAction {
    data object PaymentProcessingStarted : PaymentAction
    data object PaymentProcessingCompleted : PaymentAction
}
