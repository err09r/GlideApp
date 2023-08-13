package com.apsl.glideapp.feature.wallet.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.domain.transaction.PaymentMethod
import com.apsl.glideapp.feature.wallet.R

@Immutable
data class PaymentMethodUiModel(
    //@StringRes
    val name: String,
    @DrawableRes val iconResId: Int
)

fun PaymentMethod.toPaymentUiModel(): PaymentMethodUiModel {
    val name: String
    val imageResId = when (this) {
        PaymentMethod.GooglePay -> {
            name = "Google Pay"
            R.drawable.ic_google_pay
        }

        PaymentMethod.Blik -> {
            name = "BLIK"
            R.drawable.ic_blik
        }

        PaymentMethod.Card -> {
            name = "Debit Card"
            R.drawable.ic_card
        }
    }
    return PaymentMethodUiModel(name = name, iconResId = imageResId)
}
