package com.apsl.glideapp.feature.wallet.topup

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.PaymentMethod
import com.apsl.glideapp.feature.wallet.R
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class PaymentMethodUiModel(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
)

fun PaymentMethod.toPaymentUiModel(): PaymentMethodUiModel {
    val titleResId: Int
    val imageResId = when (this) {
        PaymentMethod.GooglePay -> {
            titleResId = CoreR.string.payment_method_google_pay
            R.drawable.ic_google_pay
        }

        PaymentMethod.Blik -> {
            titleResId = CoreR.string.payment_method_blik
            R.drawable.ic_blik
        }

        PaymentMethod.Card -> {
            titleResId = CoreR.string.payment_method_debit_card
            R.drawable.ic_debit_card
        }
    }
    return PaymentMethodUiModel(titleResId = titleResId, iconResId = imageResId)
}

fun List<PaymentMethod>.toPaymentMethods(): PaymentMethods {
    return PaymentMethods(value = this.map(PaymentMethod::toPaymentUiModel))
}
