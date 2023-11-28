package com.apsl.glideapp.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.model.ConnectivityState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("MissingPermission")
class ConnectivityObserverImpl @Inject constructor(
    @ApplicationContext context: Context
) : ConnectivityObserver {

    private val scope = CoroutineScope(Job())

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val connectivityState = callbackFlow {

        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch {
                    send(ConnectivityState.Available)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch {
                    send(ConnectivityState.Losing)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch {
                    send(ConnectivityState.Lost)
                }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch {
                    send(ConnectivityState.Unavailable)
                }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }

    }
        .onEach { Timber.d(it.toString()) }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = ConnectivityState.Initial
        )
}
