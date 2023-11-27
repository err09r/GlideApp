package com.apsl.glideapp.core.location.di

import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.domain.location.AddressDecoder
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.location.AddressDecoderImpl
import com.apsl.glideapp.core.location.ConnectivityObserverImpl
import com.apsl.glideapp.core.location.LocationClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Singleton
    @Binds
    fun bindLocationClient(locationClient: LocationClientImpl): LocationClient

    @Singleton
    @Binds
    fun bindAddressDecoder(addressDecoder: AddressDecoderImpl): AddressDecoder

    @Singleton
    @Binds
    fun bindConnectivityObserver(connectivityObserver: ConnectivityObserverImpl): ConnectivityObserver
}
