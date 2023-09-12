package com.apsl.glideapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "zone_coordinates",
    primaryKeys = ["zone_id", "latitude", "longitude"]
)
data class ZoneCoordinatesEntity(
    @ColumnInfo("zone_id")
    val zoneId: String,
    @ColumnInfo("latitude")
    val latitude: Double,
    @ColumnInfo("longitude")
    val longitude: Double,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)
