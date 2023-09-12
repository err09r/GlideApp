package com.apsl.glideapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apsl.glideapp.common.models.ZoneType
import kotlinx.datetime.LocalDateTime

@Entity("zones")
data class ZoneEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("code")
    val code: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("type")
    val type: ZoneType,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)
