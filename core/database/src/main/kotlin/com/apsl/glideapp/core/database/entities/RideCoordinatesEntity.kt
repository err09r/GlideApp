package com.apsl.glideapp.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "ride_coordinates",
    primaryKeys = ["ride_id", "latitude", "longitude"]
)
data class RideCoordinatesEntity(
    @ColumnInfo("ride_id")
    val rideId: String,
    @ColumnInfo("latitude")
    val latitude: Double,
    @ColumnInfo("longitude")
    val longitude: Double,
    @ColumnInfo("created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo("updated_at")
    val updatedAt: LocalDateTime
)
