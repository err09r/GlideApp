package com.apsl.glideapp.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apsl.glideapp.common.models.TransactionType
import kotlinx.datetime.LocalDateTime

@Entity("transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val key: Int?,
    val amount: Double,
    val type: TransactionType,
    val dateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
