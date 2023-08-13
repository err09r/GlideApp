package com.apsl.glideapp.feature.wallet.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.transaction.Transaction
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
    val dateTime: String,
    val separatorText: String,
    val value: Double = 0.0
)

@Immutable
enum class AmountType {
    Normal, Negative, Positive
}

private val formatter = DateTimeFormatter.ofPattern("d MMM, HH:mm")
private val separatorFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")

fun Transaction.toTransactionUiModel(): TransactionUiModel {
    val amountString: String
    val amountType = when {
        amount > 0.0 -> {
            amountString = "+ ${abs(amount).format(2)} PLN"
            AmountType.Positive
        }

        amount < 0.0 -> {
            amountString = "- ${abs(amount).format(2)} PLN"
            AmountType.Negative
        }

        else -> {
            amountString = "${amount.format(2)} PLN"
            AmountType.Normal
        }
    }
    return TransactionUiModel(
        value = this.amount,
        id = id,
        amount = amountString,
        amountType = amountType,
        title = type.title,
        dateTime = dateTime.toJavaLocalDateTime().format(formatter),
        separatorText = dateTime.toJavaLocalDateTime().format(separatorFormatter)
    )
}

private val TransactionType.title: String
    get() = when (this) {
        TransactionType.TopUp -> "Account top up"
        TransactionType.Ride -> "Ride"
        TransactionType.StartBonus -> "Start bonus"
        TransactionType.Voucher -> "Voucher activation"
        TransactionType.Penalty -> "Penalty"
    }
