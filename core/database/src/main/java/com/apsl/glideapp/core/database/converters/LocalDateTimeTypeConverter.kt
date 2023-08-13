package com.apsl.glideapp.core.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun toLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string)
}
