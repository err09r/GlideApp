package com.apsl.glideapp.core.model

enum class ConnectivityState {
    Initial, Available, Losing, Lost, Unavailable
}

val ConnectivityState.isConnected get() = this == ConnectivityState.Available
