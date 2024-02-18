package com.apsl.glideapp.feature.wallet.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Transaction
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.icons.Bonus
import com.apsl.glideapp.core.ui.icons.Dollar
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.icons.Voucher
import com.apsl.glideapp.core.util.android.CurrencyFormatter
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class TransactionUiModel(
    val id: String,
    val amount: String,
    val amountType: AmountType,
    @StringRes val titleResId: Int,
    val image: ImageVector,
    val dateTime: String,
    val separator: PagingSeparator
)

@Immutable
enum class AmountType {
    Normal, Negative, Positive
}

private val formatter by lazy { DateTimeFormatter.ofPattern("d MMM, HH:mm") }
private val separatorFormatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }

fun Transaction.toTransactionUiModel(): TransactionUiModel {
    var amountString = CurrencyFormatter.format(abs(amount))
    val amountType = when {
        amount > 0.0 -> {
            amountString = "+$amountString"
            AmountType.Positive
        }

        amount < 0.0 -> {
            amountString = "-$amountString"
            AmountType.Negative
        }

        else -> AmountType.Normal
    }

    val separator = PagingSeparator(text = dateTime.format(separatorFormatter).capitalized())

    return TransactionUiModel(
        id = id,
        amount = amountString,
        amountType = amountType,
        titleResId = type.titleResId,
        image = type.image,
        dateTime = dateTime.format(formatter),
        separator = separator
    )
}

private val TransactionType.image: ImageVector
    get() = when (this) {
        TransactionType.Ride -> GlideIcons.Dollar
        TransactionType.StartBonus -> GlideIcons.Bonus
        TransactionType.Voucher -> GlideIcons.Voucher
        else -> GlideIcons.TopUp
    }

private val TransactionType.titleResId: Int
    @StringRes
    get() = when (this) {
        TransactionType.TopUp -> CoreR.string.transaction_type_top_up
        TransactionType.Ride -> CoreR.string.transaction_type_ride
        TransactionType.StartBonus -> CoreR.string.transaction_type_start_bonus
        TransactionType.Voucher -> CoreR.string.transaction_type_voucher
        TransactionType.Penalty -> CoreR.string.transaction_type_penalty
    }
