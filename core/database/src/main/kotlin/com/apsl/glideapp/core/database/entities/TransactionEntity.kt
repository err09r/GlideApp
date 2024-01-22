package com.apsl.glideapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apsl.glideapp.common.models.TransactionType
import kotlinx.datetime.LocalDateTime

@Entity("transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("key")
    val key: Int?,
    @ColumnInfo("amount")
    val amount: Double,
    @ColumnInfo("type")
    val type: TransactionType,
    @ColumnInfo("date_time")
    val dateTime: LocalDateTime,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)
