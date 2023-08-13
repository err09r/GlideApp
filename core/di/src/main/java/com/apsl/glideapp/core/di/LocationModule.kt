package com.apsl.glideapp.core.di

import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.domain.location.AddressDecoder
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.location.ConnectivityObserverImpl
import com.apsl.glideapp.core.location.GlideAddressDecoder
import com.apsl.glideapp.core.location.GlideLocationClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Singleton
    @Binds
    abstract fun bindLocationClient(locationClient: GlideLocationClient): LocationClient

    @Singleton
    @Binds
    abstract fun bindAddressDecoder(addressDecoder: GlideAddressDecoder): AddressDecoder

    @Singleton
    @Binds
    abstract fun bindConnectivityObserver(connectivityObserver: ConnectivityObserverImpl): ConnectivityObserver
}
