package com.apsl.glideapp.core.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val connectivityState: Flow<ConnectionState>
}
