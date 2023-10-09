package com.apsl.glideapp.feature.home.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.util.Geometry
import com.apsl.glideapp.core.domain.auth.ObserveUserAuthenticationStateUseCase
import com.apsl.glideapp.core.domain.location.GetLastSavedUserLocationUseCase
import com.apsl.glideapp.core.domain.location.GpsDisabledException
import com.apsl.glideapp.core.domain.location.IsGpsEnabledUseCase
import com.apsl.glideapp.core.domain.location.MissingLocationPermissionsException
import com.apsl.glideapp.core.domain.location.ObserveUserLocationUseCase
import com.apsl.glideapp.core.domain.location.SaveLastUserLocationUseCase
import com.apsl.glideapp.core.domain.map.GetMapContentWithinBoundsUseCase
import com.apsl.glideapp.core.domain.map.ObserveMapContentUseCase
import com.apsl.glideapp.core.domain.ride.FinishRideUseCase
import com.apsl.glideapp.core.domain.ride.IsRideModeActiveUseCase
import com.apsl.glideapp.core.domain.ride.ObserveRideEventsUseCase
import com.apsl.glideapp.core.domain.ride.StartRideUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.model.RideEvent
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.util.maps.toCoordinates
import com.apsl.glideapp.core.util.maps.toCoordinatesBounds
import com.apsl.glideapp.core.util.maps.toLatLng
import com.apsl.glideapp.core.util.maps.toLatLngBounds
import com.apsl.glideapp.feature.home.maps.NoParkingZone
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeUserAuthenticationStateUseCase: ObserveUserAuthenticationStateUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getLastSavedUserLocationUseCase: GetLastSavedUserLocationUseCase,
    private val saveLastUserLocationUseCase: SaveLastUserLocationUseCase,
    private val getMapContentWithinBoundsUseCase: GetMapContentWithinBoundsUseCase,
    private val startRideUseCase: StartRideUseCase,
    private val finishRideUseCase: FinishRideUseCase,
    private val observeRideEventsUseCase: ObserveRideEventsUseCase,
    private val observeUserLocationUseCase: ObserveUserLocationUseCase,
    private val observeMapContentUseCase: ObserveMapContentUseCase,
    private val isRideModeActiveUseCase: IsRideModeActiveUseCase,
    private val isGpsEnabledUseCase: IsGpsEnabledUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<HomeAction>()
    val actions = _actions.receiveAsFlow()

    private var rideEventsJob: Job? = null
    private var mapContentJob: Job? = null
    private var userLocationJob: Job? = null

    private var currentRideId: String? = null

    init {
        observeUserAuthenticationState()
        getLastSavedUserLocation()
    }

    fun getUser() {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess { user ->
                    if (user == null) {
                        return@onSuccess
                    }
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

    private fun observeUserAuthenticationState() {
        observeUserAuthenticationStateUseCase()
            .distinctUntilChanged()
            .onEach { authState ->
                Timber.d("User authentication state: $authState")
                _uiState.update { it.copy(userAuthState = authState) }
            }
            .launchIn(viewModelScope)
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
                when (rideEvent) {
                    is RideEvent.Started -> onRideStarted(rideEvent.rideId)
                    is RideEvent.RouteUpdated -> onRideRouteUpdated(rideEvent.currentRoute)
                    is RideEvent.Finished -> onRideFinished()
                    is RideEvent.Error.UserInsideNoParkingZone -> {
                        _actions.send(HomeAction.Toast(rideEvent.message.toString()))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadMapContentWithinBounds(bounds: LatLngBounds) {
        Timber.d("loadMapContentWithinBounds: $bounds")
        viewModelScope.launch {
            if (isRideModeActiveUseCase()) {
                return@launch
            }
            _uiState.update { it.copy(isLoadingMapContent = true) }
            getMapContentWithinBoundsUseCase(bounds.toCoordinatesBounds())
        }
    }

    fun startRide() {
        viewModelScope.launch {
            ensureGpsEnabled().onFailure {
                Timber.d(it)
                _actions.send(HomeAction.OpenLocationSettingsDialog)
                return@launch
            }

            val currentUserLocation = uiState.value.userLocation
            val selectedVehicle = uiState.value.selectedVehicle

            if (currentUserLocation != null && selectedVehicle != null) {
                val distanceFromVehicle = Geometry.calculateDistance(
                    currentUserLocation.toCoordinates().asPair(),
                    selectedVehicle.itemPosition.toCoordinates().asPair()
                )

                Timber.d("Distance from vehicle: $distanceFromVehicle")

                if (distanceFromVehicle <= 50.0) {
                    startRideUseCase(
                        vehicleId = selectedVehicle.id,
                        userCoordinates = currentUserLocation.toCoordinates()
                    )
                } else {
                    _actions.send(HomeAction.Toast("You are too far from the vehicle"))
                }
            }
        }
    }

    fun finishRide() {
        viewModelScope.launch {
            ensureGpsEnabled().onFailure {
                _actions.send(HomeAction.OpenLocationSettingsDialog)
                return@launch
            }

            val currentUserLocation = uiState.value.userLocation
            val currentRideId = currentRideId

            if (currentUserLocation != null && currentRideId != null) {
                finishRideUseCase(
                    rideId = currentRideId,
                    userCoordinates = currentUserLocation.toCoordinates()
                )
            }
        }
    }

    private fun ensureGpsEnabled(): Result<Unit> {
        return if (isGpsEnabledUseCase()) {
            Result.success(Unit)
        } else {
            Result.failure(GpsDisabledException())
        }
    }

    private fun onRideStarted(rideId: String) {
        Timber.d("Ride started")
        currentRideId = rideId

//        stopObservingMapContent()

        _uiState.update { it.copy(rideState = RideState.Active) }

        viewModelScope.launch {
            _actions.send(HomeAction.StartRide(rideId = rideId))
        }
    }

    private fun onRideRouteUpdated(currentRoute: List<Coordinates>) {
        Timber.d("Ride route updated")

        _uiState.update { state ->
            state.copy(rideRoute = currentRoute.map(Coordinates::toLatLng))
        }
        Timber.d(uiState.value.rideRoute?.size.toString())
    }

    private fun onRideFinished() {
        Timber.d("Ride finished")

        viewModelScope.launch {
            currentRideId = null
            updateSelectedVehicle(null)

            _uiState.update { it.copy(rideRoute = null, rideState = null) }

            _actions.send(HomeAction.FinishRide)
        }
    }

    fun startObservingMapContent() {
        if (mapContentJob != null) {
            return
        }
        viewModelScope.launch {
//            if (isRideModeActiveUseCase()) {
//                stopObservingMapContent()
//                return@launch
//            }

            mapContentJob = observeMapContentUseCase()
                .onEach { result ->
                    result
                        .onSuccess { mapContent ->
                            _uiState.update { state ->
                                hideMapLoading()
                                state.copy(
                                    vehicleClusterItems = mapContent.availableVehicles.map { vehicle ->
                                        VehicleClusterItem(
                                            isSelected = state.selectedVehicle?.id == vehicle.id,
                                            id = vehicle.id,
                                            code = vehicle.code.toString().padStart(5, '0'),
                                            charge = vehicle.batteryCharge,
                                            range = (vehicle.batteryCharge * 0.4).roundToInt(),
                                            itemPosition = vehicle.coordinates.toLatLng()
                                        )
                                    },
                                    ridingZones = mapContent.ridingZones.map { zone ->
                                        zone.coordinates.map { it.toLatLng() }
                                    },
                                    noParkingZones = mapContent.noParkingZones.map { zone ->
                                        NoParkingZone(
                                            coordinates = zone.coordinates.map { it.toLatLng() },
                                            center = zone.coordinates.toLatLngBounds().center
                                        )
                                    }
                                )
                            }
                        }
                        .onFailure {
                            hideMapLoading()
                            _uiState.update { it.copy(vehicleClusterItems = emptyList()) }
                        }
                }
                .launchIn(viewModelScope)
        }
    }

    private var mapLoadingJob: Job? = null

    private suspend fun hideMapLoading() {
        mapLoadingJob?.cancelAndJoin()
        mapLoadingJob = viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(isLoadingMapContent = false) }
        }
    }

    fun stopObservingMapContent() {
        mapContentJob?.cancel()
        mapContentJob = null
    }

    fun startObservingUserLocation() {
        if (userLocationJob != null) {
            viewModelScope.launch {
                val isInRideMode = isRideModeActiveUseCase()
                val userLocation = uiState.value.userLocation
                val currentRideId = currentRideId
                if (isInRideMode && userLocation == null && currentRideId != null) {
                    _actions.send(HomeAction.RestartUserLocation(currentRideId))
                }
            }
            return
        }

        userLocationJob = observeUserLocationUseCase()
            .onEach { result ->
                result
                    .onSuccess { userLocation ->
                        val coordinates = userLocation.toCoordinates()
                        saveLastUserLocationUseCase(coordinates)
                        _uiState.update { it.copy(userLocation = userLocation) }
                    }
                    .onFailure { throwable ->
                        Timber.d(throwable.message)
                        when (throwable) {
                            is GpsDisabledException -> {
                                if (!isRideModeActiveUseCase()) {
                                    _actions.send(HomeAction.OpenLocationSettingsDialog)
                                }
                            }

                            is MissingLocationPermissionsException -> {
                                if (isRideModeActiveUseCase()) {
                                    _actions.send(HomeAction.RequestLocationPermissions)
                                }
                            }
                        }
                        stopObservingUserLocation()
                    }
            }
            .launchIn(viewModelScope)
    }

    fun stopObservingUserLocation() {
        userLocationJob?.cancel()
        userLocationJob = null
    }
}
