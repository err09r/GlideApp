@file:Suppress("UselessCallOnCollection")

package com.apsl.glideapp.core.network

import com.apsl.glideapp.common.dto.MapContentDto
import com.apsl.glideapp.common.dto.RideEventDto
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.common.models.RideAction
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber

class KtorWebSocketClient @Inject constructor(
    private val mapWebSocketSession: WebSocketSession,
    private val rideWebSocketSession: WebSocketSession,
    private val json: Json
) : WebSocketClient {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var mapSession: DefaultClientWebSocketSession? = null
    private var rideSession: DefaultClientWebSocketSession? = null

    private val mapDataToSend = MutableStateFlow<CoordinatesBounds?>(null)
    private val rideActionsToSend = ConcurrentLinkedQueue<RideAction>()

    private val _mapContent = MutableSharedFlow<Flow<MapContentDto>>()
    override val mapContent = _mapContent
        .flatMapLatest { it }
        .shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    private val mapFlow = emptyFlow<MapContentDto>()
        .onStart {
            mapSession?.close()
            mapSession = mapWebSocketSession.open()
            Timber.d("Map session opened")
            mapSession?.run {

                onMapSessionInitialized()

                for (frame in this.incoming) {
                    frame as? Frame.Text ?: continue

                    val mapContentDto = runCatching {
                        json.decodeFromString<MapContentDto>(frame.readText())
                    }.getOrNull()

//                Timber.tag("mapContentDto").d(mapContentDto.toString())

                    if (mapContentDto != null) {
                        emit(mapContentDto)
                    }
                }
            }
        }
        .flowOn(Dispatchers.IO)
        .catch {
            Timber.d("Exception in map session: $it")
            closeMapSession()
        }
        .onCompletion {
            Timber.d("Map session closed, cause: $it")
            closeMapSession()
        }

    private suspend fun DefaultClientWebSocketSession.onMapSessionInitialized() {
        withContext(Dispatchers.IO) {
            mapDataToSend
                .getAndUpdate { null }
                ?.let { sendSerialized(it) }
        }
    }

    private suspend fun closeMapSession() {
        mapDataToSend.update { null }
        mapSession?.close()
        mapSession = null
    }

    override suspend fun sendMapData(data: CoordinatesBounds) {
        Timber.d(data.toString())
        mapSession?.sendSerialized(data) ?: run {
            mapDataToSend.update { data }
            _mapContent.emit(mapFlow)
        }
    }

    private val _rideEvents = MutableSharedFlow<Flow<RideEventDto>>()
    override val rideEvents: Flow<RideEventDto> = _rideEvents
        .onStart { emit(initializingRideFlow) }
        .flatMapLatest { it }
        .shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    override suspend fun sendRideAction(action: RideAction) {
//        Timber.d("action: $action, rideSession: $rideSession")
        rideSession?.sendSerialized(action) ?: run {
            rideActionsToSend.add(action)
            _rideEvents.emit(rideFlow)
        }
    }

    private val initializingRideFlow = emptyFlow<RideEventDto>()
        .onStart {
            rideSession?.close()
            rideSession = rideWebSocketSession.open()
            Timber.d("Ride session opened")
            rideSession?.run {
                sendSerialized<RideAction>(RideAction.RequestCurrentState)
                while (isActive) {
                    val rideEventDto = receiveDeserialized<RideEventDto>()
                    if (rideEventDto is RideEventDto.SessionCancelled) {
                        closeRideSession()
                    } else {
                        emit(rideEventDto)
                    }
                }
            }
        }
        .rideFlowOperators()

    private val rideFlow = emptyFlow<RideEventDto>()
        .onStart {
            rideSession?.close()
            rideSession = rideWebSocketSession.open()
            Timber.d("Ride session opened")
            rideSession?.run {
                onRideSessionInitialized()
                while (isActive) {
                    val rideEventDto = receiveDeserialized<RideEventDto>()
                    emit(rideEventDto)
                }
            }
        }
        .rideFlowOperators()

    private suspend fun DefaultClientWebSocketSession.onRideSessionInitialized() {
        if (rideActionsToSend.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                rideActionsToSend
                    .filterNotNull()
                    .forEach { sendSerialized<RideAction>(it) }
                rideActionsToSend.clear()
            }
        }
    }

    private fun Flow<RideEventDto>.rideFlowOperators(): Flow<RideEventDto> {
        return this
            .flowOn(Dispatchers.IO)
            .catch {
                Timber.d("Exception in ride session: $it")
                closeRideSession()
            }
            .onCompletion {
                Timber.d("Ride session closed, cause: $it")
                closeRideSession()
            }
    }

    private suspend fun closeRideSession() {
        rideActionsToSend.clear()
        rideSession?.close()
        rideSession = null
    }
}
