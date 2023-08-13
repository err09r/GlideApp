package com.apsl.glideapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apsl.glideapp.core.database.converters.CoordinatesTypeConverter
import com.apsl.glideapp.core.database.converters.LocalDateTimeTypeConverter
import com.apsl.glideapp.core.database.dao.RideDao
import com.apsl.glideapp.core.database.dao.TransactionDao
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.database.entities.TransactionEntity

@Database(
    version = AppDatabase.VERSION,
    exportSchema = true,
    entities = [
        TransactionEntity::class,
        RideEntity::class
    ]
)
@TypeConverters(
    value = [
        LocalDateTimeTypeConverter::class,
        CoordinatesTypeConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun rideDao(): RideDao

    companion object {
        const val NAME = "app_database"
        const val VERSION = 1
    }
}
