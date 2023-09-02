package com.apsl.glideapp.feature.home.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.util.Geometry
import com.apsl.glideapp.core.domain.auth.GetIsUserAuthenticatedUseCase
import com.apsl.glideapp.core.domain.location.GetLastSavedUserLocationUseCase
import com.apsl.glideapp.core.domain.location.ObserveUserLocationUseCase
import com.apsl.glideapp.core.domain.location.SaveLastUserLocationUseCase
import com.apsl.glideapp.core.domain.location.StartReceivingLocationUpdatesUseCase
import com.apsl.glideapp.core.domain.location.StopReceivingLocationUpdatesUseCase
import com.apsl.glideapp.core.domain.map.LoadMapDataWithinBoundsUseCase
import com.apsl.glideapp.core.domain.map.ObserveMapStateUseCase
import com.apsl.glideapp.core.domain.ride.FinishRideUseCase
import com.apsl.glideapp.core.domain.ride.ObserveRideEventsUseCase
import com.apsl.glideapp.core.domain.ride.RideEvent
import com.apsl.glideapp.core.domain.ride.StartRideUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.apsl.glideapp.feature.home.maps.toCoordinates
import com.apsl.glideapp.feature.home.maps.toCoordinatesBounds
import com.apsl.glideapp.feature.home.maps.toLatLng
import com.apsl.glideapp.feature.home.maps.toNoParkingZone
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIsUserAuthenticatedUseCase: GetIsUserAuthenticatedUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getLastSavedUserLocationUseCase: GetLastSavedUserLocationUseCase,
    private val saveLastUserLocationUseCase: SaveLastUserLocationUseCase,
    private val loadMapDataWithinBoundsUseCase: LoadMapDataWithinBoundsUseCase,
    private val startRideUseCase: StartRideUseCase,
    private val finishRideUseCase: FinishRideUseCase,
    private val startReceivingLocationUpdatesUseCase: StartReceivingLocationUpdatesUseCase,
    private val stopReceivingLocationUpdatesUseCase: StopReceivingLocationUpdatesUseCase,
    private val observeRideEventsUseCase: ObserveRideEventsUseCase,
    private val observeUserLocationUseCase: ObserveUserLocationUseCase,
    private val observeMapStateUseCase: ObserveMapStateUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = MutableSharedFlow<HomeAction>()
    val actions = _actions.asSharedFlow()

    private var rideEventsJob: Job? = null
    private var mapStateJob: Job? = null
    private var userLocationJob: Job? = null

    private var currentRideId: String? = null

//    private var rideTimerJob: Job? = null

//    private val rideTimerFlow = flow {
//        val interval = 1.seconds
//        val res =
//            (uiState.value.rideState as? RideState.Started)?.dateTime?.toInstant(TimeZone.currentSystemDefault())
//        val initialTimerValue = res?.minus(Clock.System.now()) ?: 0.seconds
//        var timerValue = initialTimerValue
//
//        delay(interval)
//        Timber.d("init timer value: $initialTimerValue")
//        while (currentCoroutineContext().isActive && uiState.value.rideState is RideState.Started) {
//            emit(timerValue)
//            delay(interval)
//            timerValue += interval
//        }
//    }
//        .onEach {
//            _uiState.update { state ->
//                state.copy(
//                    rideTimerValue = it.toIsoString()
//                        .removePrefix("PT")
//                        .replace("[HMS]".toRegex(), ":")
//                        .removeSuffix(":")
//                )
//            }
//        }

    init {
        getIsUserAuthenticated()
        getUser()
        getLastSavedUserLocation()
    }

    private fun getIsUserAuthenticated() {
        viewModelScope.launch {
            getIsUserAuthenticatedUseCase()
                .onSuccess { isAuthenticated ->
                    _uiState.update { state ->
                        state.copy(
                            userAuthState = when {
                                isAuthenticated -> UserAuthState.Authenticated
                                else -> UserAuthState.NotAuthenticated
                            }
                        )
                    }
                }
                .onFailure(Timber::d)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess { user ->
                    _uiState.update { state ->
                        state.copy(
                            username = user.username,
                            userTotalDistance = user.totalDistance.roundToInt(),
                            userTotalRides = user.totalRides,
                            userBalance = user.balance,
                        )
                    }
                }
                .onFailure(Timber::d)
        }
    }

    private fun getLastSavedUserLocation() {
        viewModelScope.launch {
            getLastSavedUserLocationUseCase().onSuccess { coordinates ->
                val initialCameraPosition = coordinates?.toLatLng()
                _uiState.update { it.copy(initialCameraPosition = initialCameraPosition) }
            }
        }
    }

    fun startReceivingLocationUpdates() {
        viewModelScope.launch {
            startReceivingLocationUpdatesUseCase().onFailure(Timber::d)
        }
    }

    fun stopReceivingLocationUpdates() {
        viewModelScope.launch {
            stopReceivingLocationUpdatesUseCase().onFailure(Timber::d)
        }
    }

    fun updateSelectedVehicle(vehicle: VehicleClusterItem?) {
        _uiState.update { state ->
            state.copy(
                selectedVehicle = vehicle,
                vehicleClusterItems = state.vehicleClusterItems.map {
                    when {
                        it.isSelected -> it.copy(isSelected = false)
                        it.code == (vehicle?.code ?: false) -> it.copy(isSelected = true)
                        else -> it
                    }
                }
            )
        }
    }

    fun startReceivingRideEvents() {
        if (rideEventsJob != null) {
            return
        }
        rideEventsJob = observeRideEventsUseCase()
            .mapNotNull { it.getOrNull() }
            .onEach { rideEvent ->
                Timber.d("onEach: $rideEvent")
                when (rideEvent) {
                    is RideEvent.Started -> onRideStarted(rideEvent.rideId)
                    is RideEvent.RouteUpdated -> onRideRouteUpdated(rideEvent.currentRoute)
                    is RideEvent.Finished -> onRideFinished()
                    is RideEvent.Error.UserInsideNoParkingZone -> {
                        _actions.emit(HomeAction.Toast(rideEvent.message.toString()))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadMapDataWithinBounds(bounds: LatLngBounds) {
        viewModelScope.launch {
            loadMapDataWithinBoundsUseCase(bounds.toCoordinatesBounds())
        }
    }

    fun startRide() {
        viewModelScope.launch {
            val currentUserLocation = uiState.value.userLocation
            val selectedVehicle = uiState.value.selectedVehicle

            if (currentUserLocation != null && selectedVehicle != null) {
                val distanceFromVehicle = Geometry.calculateDistance(
                    currentUserLocation.toCoordinates(),
                    selectedVehicle.itemPosition.toCoordinates()
                )

                Timber.d("Distance from vehicle: $distanceFromVehicle")

                if (distanceFromVehicle <= 25.0) {
                    startRideUseCase(
                        vehicleId = selectedVehicle.id,
                        userCoordinates = currentUserLocation.toCoordinates()
                    )
                } else {
                    _actions.emit(HomeAction.Toast("You are too far from the vehicle"))
                }
            }
        }
    }

    fun finishRide() {
        viewModelScope.launch {
            val currentUserLocation = uiState.value.userLocation
            val selectedVehicleId = uiState.value.selectedVehicle?.id
            val currentRideId = currentRideId

            if (currentUserLocation != null && selectedVehicleId != null && currentRideId != null) {
                finishRideUseCase(
                    rideId = currentRideId,
                    vehicleId = selectedVehicleId,
                    userCoordinates = currentUserLocation.toCoordinates()
                )
            }
        }
    }

    private fun onRideStarted(rideId: String) {
        Timber.d("started")
        currentRideId = rideId

        _uiState.update { it.copy(rideState = RideState.Started) }

        viewModelScope.launch {
            _actions.emit(HomeAction.RideStarted(rideId = rideId))
        }
//        rideTimerJob = rideTimerFlow.launchIn(viewModelScope)
    }

    private fun onRideRouteUpdated(currentRoute: List<Coordinates>) {
        Timber.d("updated")

        _uiState.update { state ->
            state.copy(rideRoute = currentRoute.map(Coordinates::toLatLng))
        }
        Timber.d(uiState.value.rideRoute?.size.toString())
    }

    private fun onRideFinished() {
        Timber.d("finished")

        viewModelScope.launch {
            currentRideId = null
//            rideTimerJob?.cancel()
//            rideTimerJob = null
            updateSelectedVehicle(null)

            _uiState.update { it.copy(rideRoute = null, rideState = null) }

            _actions.emit(HomeAction.RideFinished)
        }
    }

    fun startObservingMapState() {
        viewModelScope.launch {
            mapStateJob = observeMapStateUseCase()
//                .mapNotNull { it.getOrNull() }
                .onEach { mapState ->
                    _uiState.update { state ->
                        state.copy(
                            vehicleClusterItems = mapState.availableVehicles.map { vehicle ->
                                VehicleClusterItem(
                                    isSelected = state.selectedVehicle?.id == vehicle.id,
                                    id = vehicle.id,
                                    code = vehicle.code.toString().padStart(5, '0'),
                                    charge = vehicle.batteryCharge,
                                    range = (vehicle.batteryCharge * 0.4).roundToInt(),
                                    itemPosition = vehicle.coordinates.toLatLng()
                                )
                            },
                            ridingZones = mapState.ridingZones.map { zone ->
                                zone.coordinates.map(Coordinates::toLatLng)
                            },
                            noParkingZones = mapState.noParkingZones.map { zone ->
                                zone.coordinates.toNoParkingZone()
                            }
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun stopObservingMapState() {
        mapStateJob?.cancel()
        mapStateJob = null
    }

    fun startObservingUserLocation() {
        viewModelScope.launch {
            userLocationJob = observeUserLocationUseCase()
                .onEach { userLocation ->
                    val coordinates = userLocation.toCoordinates()
                    saveLastUserLocationUseCase(coordinates)

                    _uiState.update { it.copy(userLocation = userLocation) }
                }
                .launchIn(viewModelScope)
        }
    }

    fun stopObservingUserLocation() {
        userLocationJob?.cancel()
    }
}
