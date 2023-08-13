package com.apsl.glideapp.core.database.converters

import androidx.room.TypeConverter
import com.apsl.glideapp.common.models.Coordinates
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CoordinatesTypeConverter {

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String = Json.encodeToString(coordinates)

    @TypeConverter
    fun toCoordinates(string: String): Coordinates = Json.decodeFromString(string)

    @TypeConverter
    fun fromCoordinatesList(list: List<Coordinates>): String = Json.encodeToString(list)

    @TypeConverter
    fun toCoordinatesList(string: String): List<Coordinates> = Json.decodeFromString(string)
}
