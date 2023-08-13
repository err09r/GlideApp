package com.apsl.glideapp.core.di

import com.apsl.glideapp.core.data.repositories.AuthRepositoryImpl
import com.apsl.glideapp.core.data.repositories.LocationRepositoryImpl
import com.apsl.glideapp.core.data.repositories.MapRepositoryImpl
import com.apsl.glideapp.core.data.repositories.RideRepositoryImpl
import com.apsl.glideapp.core.data.repositories.TransactionRepositoryImpl
import com.apsl.glideapp.core.data.repositories.UserRepositoryImpl
import com.apsl.glideapp.core.domain.location.LocationRepository
import com.apsl.glideapp.core.domain.map.MapRepository
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.domain.transaction.TransactionRepository
import com.apsl.glideapp.core.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): com.apsl.glideapp.core.domain.auth.AuthRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(repository: MapRepositoryImpl): MapRepository

    @Singleton
    @Binds
    abstract fun bindRideRepository(repository: RideRepositoryImpl): RideRepository

    @Singleton
    @Binds
    abstract fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository
}
