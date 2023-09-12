package com.apsl.glideapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apsl.glideapp.core.database.converters.LocalDateTimeTypeConverter
import com.apsl.glideapp.core.database.dao.RideCoordinatesDao
import com.apsl.glideapp.core.database.dao.RideDao
import com.apsl.glideapp.core.database.dao.TransactionDao
import com.apsl.glideapp.core.database.dao.ZoneCoordinatesDao
import com.apsl.glideapp.core.database.dao.ZoneDao
import com.apsl.glideapp.core.database.entities.RideCoordinatesEntity
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.database.entities.TransactionEntity
import com.apsl.glideapp.core.database.entities.ZoneCoordinatesEntity
import com.apsl.glideapp.core.database.entities.ZoneEntity

@Database(
    version = AppDatabase.VERSION,
    exportSchema = true,
    entities = [
        ZoneEntity::class,
        ZoneCoordinatesEntity::class,
        RideEntity::class,
        RideCoordinatesEntity::class,
        TransactionEntity::class
    ]
)
@TypeConverters(value = [LocalDateTimeTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun zoneDao(): ZoneDao
    abstract fun zoneCoordinatesDao(): ZoneCoordinatesDao
    abstract fun rideDao(): RideDao
    abstract fun rideCoordinatesDao(): RideCoordinatesDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        const val NAME = "app_database"
        const val VERSION = 1
    }
}
