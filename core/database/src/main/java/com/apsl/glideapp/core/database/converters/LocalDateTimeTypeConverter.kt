@file:Suppress("FunctionName")

package com.apsl.glideapp.core.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun LocalDateTimeToString(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun StringToLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string)
}
