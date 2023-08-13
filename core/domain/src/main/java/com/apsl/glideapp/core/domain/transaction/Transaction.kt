package com.apsl.glideapp.core.domain.transaction

import com.apsl.glideapp.common.models.TransactionType
import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val type: TransactionType,
    val dateTime: LocalDateTime
)
