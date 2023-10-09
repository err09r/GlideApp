package com.apsl.glideapp.core.domain.connectivity

import com.apsl.glideapp.core.model.ConnectivityState
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val connectivityState: Flow<ConnectivityState>
}
