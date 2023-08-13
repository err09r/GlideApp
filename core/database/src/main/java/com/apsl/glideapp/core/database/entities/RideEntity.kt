package com.apsl.glideapp.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apsl.glideapp.common.models.Coordinates
import kotlinx.datetime.LocalDateTime

@Entity("rides")
data class RideEntity(
    @PrimaryKey
    val id: String,
    val key: Int?,
    val startAddress: String?,
    val finishAddress: String?,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime,
    val route: List<Coordinates>,
    val distance: Double,
    val averageSpeed: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
