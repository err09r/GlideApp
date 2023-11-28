package com.apsl.glideapp.core.data.di

import com.apsl.glideapp.core.data.repositories.AppConfigRepositoryImpl
import com.apsl.glideapp.core.data.repositories.AuthRepositoryImpl
import com.apsl.glideapp.core.data.repositories.LocationRepositoryImpl
import com.apsl.glideapp.core.data.repositories.MapRepositoryImpl
import com.apsl.glideapp.core.data.repositories.RideRepositoryImpl
import com.apsl.glideapp.core.data.repositories.TransactionRepositoryImpl
import com.apsl.glideapp.core.data.repositories.UserRepositoryImpl
import com.apsl.glideapp.core.data.repositories.ZoneRepositoryImpl
import com.apsl.glideapp.core.domain.auth.AuthRepository
import com.apsl.glideapp.core.domain.config.AppConfigRepository
import com.apsl.glideapp.core.domain.location.LocationRepository
import com.apsl.glideapp.core.domain.map.MapRepository
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.domain.transaction.TransactionRepository
import com.apsl.glideapp.core.domain.user.UserRepository
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Singleton
    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindMapRepository(repository: MapRepositoryImpl): MapRepository

    @Singleton
    @Binds
    fun bindRideRepository(repository: RideRepositoryImpl): RideRepository

    @Singleton
    @Binds
    fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository

    @Singleton
    @Binds
    fun bindZoneRepository(repository: ZoneRepositoryImpl): ZoneRepository

    @Singleton
    @Binds
    fun bindAppConfigRepository(repository: AppConfigRepositoryImpl): AppConfigRepository
}
