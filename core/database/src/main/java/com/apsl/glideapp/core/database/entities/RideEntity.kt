package com.apsl.glideapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity("rides")
data class RideEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("key")
    val key: Int?,
    @ColumnInfo("start_address")
    val startAddress: String?,
    @ColumnInfo("finish_address")
    val finishAddress: String?,
    @ColumnInfo("start_date_time")
    val startDateTime: LocalDateTime,
    @ColumnInfo("finish_date_time")
    val finishDateTime: LocalDateTime,
    @ColumnInfo("distance")
    val distance: Double,
    @ColumnInfo("average_speed")
    val averageSpeed: Double,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)
