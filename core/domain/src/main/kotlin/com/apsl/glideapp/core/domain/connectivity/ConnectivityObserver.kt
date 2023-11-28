@file:Suppress("Unused")

package com.apsl.glideapp.core.domain.connectivity

import com.apsl.glideapp.core.model.ConnectivityState
import com.apsl.glideapp.core.model.isConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface ConnectivityObserver {
    val connectivityState: Flow<ConnectivityState>
}

suspend fun Flow<ConnectivityState>.isConnected(): Boolean {
    return this.firstOrNull()?.isConnected ?: false
}
