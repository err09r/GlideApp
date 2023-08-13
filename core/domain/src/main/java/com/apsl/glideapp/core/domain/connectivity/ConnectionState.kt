package com.apsl.glideapp.core.domain.connectivity

enum class ConnectionState {
    Initial, Available, Losing, Lost, Unavailable
}

val ConnectionState.isConnected get() = this == ConnectionState.Available
