package com.apsl.glideapp.feature.wallet.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.PaymentMethod
import com.apsl.glideapp.feature.wallet.R
import com.apsl.glideapp.feature.wallet.viewmodels.PaymentMethods

@Immutable
data class PaymentMethodUiModel(
    //@StringRes
    val title: String,
    @DrawableRes val iconResId: Int
)

fun PaymentMethod.toPaymentUiModel(): PaymentMethodUiModel {
    val title: String
    val imageResId = when (this) {
        PaymentMethod.GooglePay -> {
            title = "Google Pay"
            R.drawable.ic_google_pay
        }

        PaymentMethod.Blik -> {
            title = "BLIK"
            R.drawable.ic_blik
        }

        PaymentMethod.Card -> {
            title = "Debit Card"
            R.drawable.ic_debit_card
        }
    }
    return PaymentMethodUiModel(title = title, iconResId = imageResId)
}

fun List<PaymentMethod>.toPaymentMethods(): PaymentMethods {
    return PaymentMethods(value = this.map(PaymentMethod::toPaymentUiModel))
}
