package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.core.domain.ride.PreRideInfoEventRepository
import com.apsl.glideapp.core.model.PreRideInfoEvent
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class PreRideInfoEventRepositoryImpl @Inject constructor() : PreRideInfoEventRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _preRideInfoEvents: MutableSharedFlow<PreRideInfoEvent> = MutableSharedFlow()
    override val preRideInfoEvents: Flow<PreRideInfoEvent> = _preRideInfoEvents
        .shareIn(scope = scope, started = SharingStarted.Eagerly)

    override suspend fun send(event: PreRideInfoEvent) {
        _preRideInfoEvents.emit(event)
    }
}
