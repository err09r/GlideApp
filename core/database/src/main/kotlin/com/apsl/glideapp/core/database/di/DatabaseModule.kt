package com.apsl.glideapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.apsl.glideapp.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = AppDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(database: AppDatabase) = database.transactionDao()

    @Singleton
    @Provides
    fun provideRideDao(database: AppDatabase) = database.rideDao()

    @Singleton
    @Provides
    fun provideRideCoordinatesDao(database: AppDatabase) = database.rideCoordinatesDao()

    @Singleton
    @Provides
    fun provideZoneDao(database: AppDatabase) = database.zoneDao()

    @Singleton
    @Provides
    fun provideZoneCoordinatesDao(database: AppDatabase) = database.zoneCoordinatesDao()
}
