package com.apsl.glideapp.feature.home.rideservice

import com.apsl.glideapp.core.domain.location.DecodeAddressUseCase
import com.apsl.glideapp.core.domain.location.ObserveUserLocationUseCase
import com.apsl.glideapp.core.domain.ride.UpdateRideRouteUseCase
import com.apsl.glideapp.feature.home.maps.toCoordinates
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AssistedFactory
interface RideServiceControllerFactory {
    fun create(scope: CoroutineScope): RideServiceController
}

class RideServiceController @AssistedInject constructor(
    @Assisted private val scope: CoroutineScope,
    private val observeUserLocationUseCase: ObserveUserLocationUseCase,
    private val decodeAddressUseCase: DecodeAddressUseCase,
    private val updateRideRouteUseCase: UpdateRideRouteUseCase
) {

    private val _currentAddress = MutableStateFlow<String?>(null)
    val currentAddress = _currentAddress.asStateFlow()

    private var userLocationJob: Job? = null

    fun onServiceStart(rideId: String) {
        scope.launch {
            startObservingUserLocation(rideId)
        }
    }

    fun startObservingUserLocation(rideId: String) {
        userLocationJob?.cancel()
        userLocationJob = null
        userLocationJob = observeUserLocationUseCase()
            .onEach { result ->
                result
                    .onSuccess { userLocation ->
                        val coordinates = userLocation.toCoordinates()
                        updateRideRouteUseCase(rideId = rideId, userCoordinates = coordinates)
                        val address = decodeAddressUseCase(coordinates).getOrNull()
                        if (address != null) {
                            _currentAddress.emit(address)
                        }
                    }
                    .onFailure {
                        Timber.d(it)
                        userLocationJob?.cancel()
                        userLocationJob = null
                    }
            }
            .launchIn(scope)
    }
}
