package com.apsl.glideapp.feature.wallet.viewmodels

sealed interface PaymentAction {
    object PaymentProcessingStarted : PaymentAction
    object PaymentProcessingCompleted : PaymentAction
}
