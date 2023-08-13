package com.apsl.glideapp.core.network

import com.apsl.glideapp.common.dto.MapStateDto
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.core.util.DispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber

class KtorWebSocketClient @Inject constructor(
    private val httpClient: HttpClient,
    private val dispatchers: DispatcherProvider
) : WebSocketClient {

    private val scope = CoroutineScope(SupervisorJob() + dispatchers.io)

    private var mapSession: DefaultClientWebSocketSession? = null
    private var rideSession: DefaultClientWebSocketSession? = null

    private val rideActionsQueue = ConcurrentLinkedQueue<RideAction>()
    private val mapDataQueue = ConcurrentLinkedQueue<CoordinatesBounds>()

    override fun getMapStateUpdates(authToken: String?): Flow<MapStateDto> = flow {
        mapSession = httpClient.webSocketSession {
//            url("ws://api-glide.herokuapp.com/api/map")
            url("ws://10.0.2.2/api/map")
            header("Authorization", "Bearer $authToken")
        }

        mapSession?.let { session ->

            onMapSessionInitialized()

            for (frame in session.incoming) {
                frame as? Frame.Text ?: continue

                val mapStateDto = runCatching {
                    Json.decodeFromString<MapStateDto>(frame.readText())
                }.getOrNull()

//                Timber.tag("mapStateDto").d(mapStateDto.toString())

                if (mapStateDto != null) {
                    emit(mapStateDto)
                }
            }
        }
    }
        .flowOn(dispatchers.io)
        .catch {
            Timber.d("Exception in map session: $it")
        }
        .onStart {
            Timber.d("map session opened")
        }
        .onCompletion {
            Timber.d("map session closed, cause: $it")
            closeMapSession()
        }
        .shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    override fun getRideStateUpdates(authToken: String?): Flow<RideEventDto> = flow {
        rideSession = httpClient.webSocketSession {
//            url("ws://api-glide.herokuapp.com/api/ride")
            url("ws://10.0.2.2/api/ride")
            header("Authorization", "Bearer $authToken")
        }

        rideSession?.let { session ->

            onRideSessionInitialized()

            for (frame in session.incoming) {
                frame as? Frame.Text ?: continue

                val rideEventDto = runCatching {
                    Json.decodeFromString<RideEventDto>(frame.readText())
                }.getOrNull()

                Timber.tag("rideEventDto").d(rideEventDto.toString())

                if (rideEventDto != null) {
                    emit(rideEventDto)
                }
            }
        }
    }
        .flowOn(dispatchers.io)
        .catch {
            Timber.d("Exception in ride session: $it")
        }
        .onStart {
            Timber.d("ride session opened")
        }
        .onCompletion {
            Timber.d("ride session closed, cause: $it")
            closeRideSession()
        }
        .shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    override suspend fun sendMapData(data: CoordinatesBounds) {
//        Timber.d(data.toString())
        mapSession?.sendSerialized(data) ?: mapDataQueue.add(data)
    }

    override suspend fun sendRideAction(action: RideAction) {
        Timber.d(action.toString())
        rideSession?.sendSerialized(action) ?: rideActionsQueue.add(action)
    }

    override suspend fun closeMapSession() {
        mapDataQueue.clear()
        mapSession?.close()
        mapSession = null
    }

    override suspend fun closeRideSession() {
        rideActionsQueue.clear()
        rideSession?.close()
        rideSession = null
    }

    private suspend fun onMapSessionInitialized() {
        if (mapDataQueue.isNotEmpty()) {
            withContext(dispatchers.io) {
                mapDataQueue.filterNotNull().forEach { sendMapData(it) }
                mapDataQueue.clear()
            }
        }
    }

    private suspend fun onRideSessionInitialized() {
        if (rideActionsQueue.isNotEmpty()) {
            withContext(dispatchers.io) {
                rideActionsQueue.filterNotNull().forEach { sendRideAction(it) }
                rideActionsQueue.clear()
            }
        }
    }
}
