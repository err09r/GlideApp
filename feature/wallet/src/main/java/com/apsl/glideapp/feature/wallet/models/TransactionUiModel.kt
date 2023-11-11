package com.apsl.glideapp.feature.wallet.models

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
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlinx.datetime.toJavaLocalDateTime

@Immutable
data class TransactionUiModel(
    val id: String,
    val amount: String,
    val amountType: AmountType,
//    @StringRes val titleResId: Int,
    val title: String,
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
    val amountString: String
    val amountType = when {
        amount > 0.0 -> {
            amountString = "+${abs(amount).format(2)} zł"
            AmountType.Positive
        }

        amount < 0.0 -> {
            amountString = "-${abs(amount).format(2)} zł"
            AmountType.Negative
        }

        else -> {
            amountString = "${amount.format(2)} zł"
            AmountType.Normal
        }
    }

    val separator = PagingSeparator(
        text = dateTime.toJavaLocalDateTime().format(separatorFormatter).capitalized()
    )

    return TransactionUiModel(
        id = id,
        amount = amountString,
        amountType = amountType,
        title = type.title,
        image = type.image,
        dateTime = dateTime.toJavaLocalDateTime().format(formatter),
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

private val TransactionType.title: String
    get() = when (this) {
        TransactionType.TopUp -> "Account top up"
        TransactionType.Ride -> "Ride"
        TransactionType.StartBonus -> "Start bonus"
        TransactionType.Voucher -> "Voucher activation"
        TransactionType.Penalty -> "Penalty"
    }
