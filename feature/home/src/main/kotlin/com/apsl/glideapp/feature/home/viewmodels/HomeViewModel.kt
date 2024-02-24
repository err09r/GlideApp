package com.apsl.glideapp.feature.home.viewmodels

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.common.models.Route
import com.apsl.glideapp.common.util.Geometry
import com.apsl.glideapp.core.domain.auth.LogOutUseCase
import com.apsl.glideapp.core.domain.auth.ObserveUserAuthenticationStateUseCase
import com.apsl.glideapp.core.domain.config.GetAppConfigUseCase
import com.apsl.glideapp.core.domain.location.GetLastMapCameraPositionUseCase
import com.apsl.glideapp.core.domain.location.GpsDisabledException
import com.apsl.glideapp.core.domain.location.IsGpsEnabledUseCase
import com.apsl.glideapp.core.domain.location.MissingLocationPermissionsException
import com.apsl.glideapp.core.domain.location.ObserveUserLocationUseCase
import com.apsl.glideapp.core.domain.location.SaveLastMapCameraPositionUseCase
import com.apsl.glideapp.core.domain.map.GetMapContentWithinBoundsUseCase
import com.apsl.glideapp.core.domain.map.ObserveMapContentUseCase
import com.apsl.glideapp.core.domain.ride.FinishRideUseCase
import com.apsl.glideapp.core.domain.ride.IsRideModeActiveUseCase
import com.apsl.glideapp.core.domain.ride.ObservePreRideInfoEventsUseCase
import com.apsl.glideapp.core.domain.ride.ObserveRideEventsUseCase
import com.apsl.glideapp.core.domain.ride.StartRideUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.domain.user.SaveWalletVisitedUseCase
import com.apsl.glideapp.core.model.MapCameraPosition
import com.apsl.glideapp.core.model.PreRideInfoEvent
import com.apsl.glideapp.core.model.RideEvent
import com.apsl.glideapp.core.model.UserAuthState
import com.apsl.glideapp.core.model.UserLocation
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.util.android.DistanceFormatter
import com.apsl.glideapp.core.util.android.NumberFormatter
import com.apsl.glideapp.core.util.maps.mapToLatLng
import com.apsl.glideapp.core.util.maps.toCoordinates
import com.apsl.glideapp.core.util.maps.toCoordinatesBounds
import com.apsl.glideapp.feature.home.models.VehicleClusterItem
import com.apsl.glideapp.feature.home.models.ZoneUiModel
import com.apsl.glideapp.feature.home.models.mapToClusterItem
import com.apsl.glideapp.feature.home.models.mapToUiModel
import com.apsl.glideapp.feature.home.models.toSelectedVehicleUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import com.apsl.glideapp.core.ui.R as CoreR

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeUserAuthenticationStateUseCase: ObserveUserAuthenticationStateUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveWalletVisitedUseCase: SaveWalletVisitedUseCase,
    private val getLastMapCameraPositionUseCase: GetLastMapCameraPositionUseCase,
    private val saveLastMapCameraPositionUseCase: SaveLastMapCameraPositionUseCase,
    private val getMapContentWithinBoundsUseCase: GetMapContentWithinBoundsUseCase,
    private val startRideUseCase: StartRideUseCase,
    private val finishRideUseCase: FinishRideUseCase,
    private val observeRideEventsUseCase: ObserveRideEventsUseCase,
    private val observeUserLocationUseCase: ObserveUserLocationUseCase,
    private val observeMapContentUseCase: ObserveMapContentUseCase,
    private val isRideModeActiveUseCase: IsRideModeActiveUseCase,
    private val isGpsEnabledUseCase: IsGpsEnabledUseCase,
    private val getAppConfigUseCase: GetAppConfigUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val observePreRideInfoEventsUseCase: ObservePreRideInfoEventsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<HomeAction>()
    val actions = _actions.receiveAsFlow()

    private var vehiclesOnMap: List<Vehicle> = emptyList()
    private var unlockDistance: Double? = null
    private var activeRideId: String? = null
    private var activeRideStartDateTime: LocalDateTime? = null

    private var rideEventsJob: Job? = null
    private var mapContentJob: Job? = null
    private var userLocationJob: Job? = null
    private var mapLoadingJob: Job? = null

    init {
        observeUserAuthenticationState()
        getUnlockDistance()
        observePreRideInfoEvents()
    }

    private fun observeUserAuthenticationState() {
        observeUserAuthenticationStateUseCase()
            .distinctUntilChanged()
            .onEach { authState ->
                Timber.d("User authentication state: $authState")
                if (authState == UserAuthState.NotAuthenticated) {
                    _actions.send(HomeAction.LogOut)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observePreRideInfoEvents() {
        viewModelScope.launch {
            observePreRideInfoEventsUseCase().collect { preRideInfoEvent ->
                when (preRideInfoEvent) {
                    PreRideInfoEvent.Accepted -> startRide()
                    else -> Unit // Ignore
                }
            }
        }
    }

    fun getLastMapCameraPosition() {
        viewModelScope.launch {
            getLastMapCameraPositionUseCase().onSuccess { cameraPosition ->
                if (cameraPosition != null) {
                    _uiState.update { state ->
                        state.copy(
                            initialCameraPosition = LatLng(
                                cameraPosition.latitude,
                                cameraPosition.longitude
                            ) to cameraPosition.zoom
                        )
                    }
                }
            }
        }
    }

    private fun getUnlockDistance() {
        viewModelScope.launch {
            val distance = getAppConfigUseCase().getOrNull()?.unlockDistanceMeters
            this@HomeViewModel.unlockDistance = distance
            updateMapState(selectedVehicleRadius = distance)
        }
    }

    private fun updateMapState(
        userLocation: UserLocation? = uiState.value.mapState.userLocation,
        vehicleClusterItems: List<VehicleClusterItem> = uiState.value.mapState.vehicleClusterItems,
        ridingZones: List<List<LatLng>> = uiState.value.mapState.ridingZones,
        noParkingZones: List<ZoneUiModel> = uiState.value.mapState.noParkingZones,
        rideRoute: List<LatLng>? = uiState.value.mapState.rideRoute,
        selectedVehicleRadius: Double? = uiState.value.mapState.selectedVehicleRadiusMeters
    ) {
        _uiState.update { uiState ->
            uiState.copy(
                mapState = uiState.mapState.copy(
                    userLocation = userLocation,
                    vehicleClusterItems = vehicleClusterItems,
                    ridingZones = ridingZones,
                    noParkingZones = noParkingZones,
                    rideRoute = rideRoute,
                    selectedVehicleRadiusMeters = selectedVehicleRadius
                )
            )
        }
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
                            userInfo = state.userInfo.copy(
                                username = user.username,
                                totalDistanceKilometers = DistanceFormatter.format(user.totalDistanceMeters / 1000),
                                totalRides = NumberFormatter.format(user.totalRides),
                                walletVisited = user.walletVisited
                            )
                        )
                    }
                }
                .onFailure { Timber.d(it.message) }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

    fun updateSelectedVehicle(id: String?) {
        if (id == null) {
            _uiState.update { it.copy(selectedVehicle = null) }
            updateVehicleClusterItems(selectedId = null)
            return
        }

        val selectedVehicle = vehiclesOnMap.find { it.id == id }

        if (selectedVehicle != null) {
            _uiState.update { it.copy(selectedVehicle = selectedVehicle.toSelectedVehicleUiModel()) }
            updateVehicleClusterItems(selectedId = id)
        }
    }

    fun saveWalletVisited() {
        viewModelScope.launch {
            saveWalletVisitedUseCase().onFailure { Timber.d(it.message) }
        }
    }

    private fun updateVehicleClusterItems(selectedId: String?) {
        updateMapState(
            vehicleClusterItems = uiState.value.mapState.vehicleClusterItems.map { item ->
                item.copy(isSelected = item.id == selectedId)
            }
        )
    }

    fun loadMapContentWithinBounds(bounds: LatLngBounds, zoom: Float) {
        val contentBounds = bounds.toCoordinatesBounds()
        Timber.d("loadMapContentWithinBounds: $contentBounds")
        viewModelScope.launch {
            if (isRideModeActiveUseCase()) {
                Timber.d("Map content can't be loaded, ride is active.")
                return@launch
            }
            if (activeRideId == null && mapContentJob == null) {
                startObservingMapContent()
            }
            if (!uiState.value.isLoadingMapContent) {
                _uiState.update { it.copy(isLoadingMapContent = true) }
            }
            saveLastMapCameraPositionUseCase(
                MapCameraPosition(
                    latitude = contentBounds.center.latitude,
                    longitude = contentBounds.center.longitude,
                    zoom = zoom
                )
            )
            getMapContentWithinBoundsUseCase(contentBounds)
        }
    }

    private fun startRide() {
        viewModelScope.launch {
            checkGpsEnabled().onFailure {
                Timber.d(it)
                _actions.send(HomeAction.OpenLocationSettingsDialog)
                return@launch
            }

            val currentUserLocation = uiState.value.mapState.userLocation
            val selectedVehicle = vehiclesOnMap.find { it.id == uiState.value.selectedVehicle?.id }

            if (currentUserLocation != null && selectedVehicle != null) {
                val distanceFromVehicleMeters = Geometry.calculateDistance(
                    currentUserLocation.toCoordinates().asPair(),
                    selectedVehicle.coordinates.asPair()
                )

                Timber.d("Distance from vehicle: $distanceFromVehicleMeters")

                val unlockDistanceMeters = this@HomeViewModel.unlockDistance
                    ?: getAppConfigUseCase().getOrNull()?.unlockDistanceMeters

                if (unlockDistanceMeters != null && distanceFromVehicleMeters <= unlockDistanceMeters) {
                    startRideUseCase(
                        vehicleId = selectedVehicle.id,
                        userCoordinates = currentUserLocation.toCoordinates()
                    )
                } else {
                    val textResId = if (unlockDistanceMeters == null) {
                        CoreR.string.toast_configuration_not_received
                    } else {
                        CoreR.string.toast_user_too_far
                    }
                    _actions.send(HomeAction.Toast(textResId))
                }
            }
        }
    }

    fun finishRide() {
        viewModelScope.launch {
            checkGpsEnabled().onFailure {
                _actions.send(HomeAction.OpenLocationSettingsDialog)
                return@launch
            }

            val currentUserLocation = uiState.value.mapState.userLocation
            val activeRideId = activeRideId

            if (currentUserLocation != null && activeRideId != null) {
                finishRideUseCase(
                    rideId = activeRideId,
                    userCoordinates = currentUserLocation.toCoordinates()
                )
            }
        }
    }

    private fun checkGpsEnabled(): Result<Unit> {
        return if (isGpsEnabledUseCase()) {
            Result.success(Unit)
        } else {
            Result.failure(GpsDisabledException())
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
                    is RideEvent.Started -> onRideStarted(
                        rideId = rideEvent.rideId,
                        vehicle = rideEvent.vehicle,
                        dateTime = rideEvent.dateTime
                    )

                    is RideEvent.RouteUpdated -> onRideRouteUpdated(rideEvent.currentRoute)
                    is RideEvent.Finished -> onRideFinished(
                        distance = rideEvent.distance,
                        averageSpeed = rideEvent.averageSpeed
                    )

                    is RideEvent.Error -> onError(rideEvent)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onRideStarted(rideId: String, vehicle: Vehicle, dateTime: LocalDateTime) {
        Timber.d("Ride started")
        activeRideId = rideId
        activeRideStartDateTime = dateTime

        stopObservingMapContent()

        viewModelScope.launch {
            updateSelectedVehicle(null)
            _uiState.update {
                it.copy(
                    rideState = RideState(
                        vehicle = vehicle.toVehicleUiModel(),
                        startDateTime = dateTime
                    )
                )
            }
            _actions.send(
                HomeAction.StartRide(
                    rideId = rideId,
                    startDateTime = dateTime.toString()
                )
            )
        }
    }

    private fun onRideRouteUpdated(currentRoute: Route) {
        Timber.d("Ride route updated")
        updateMapState(rideRoute = currentRoute.points.mapToLatLng())
        updateRideState(distanceKilometers = DistanceFormatter.format(currentRoute.distance / 1000))
    }

    private fun updateRideState(
        distanceKilometers: String = uiState.value.rideState?.distanceKilometers
            ?: DistanceFormatter.default(),
        isPaused: Boolean = uiState.value.rideState?.isPaused ?: false
    ) {
        _uiState.update { uiState ->
            uiState.copy(
                rideState = uiState.rideState?.copy(
                    distanceKilometers = distanceKilometers,
                    isPaused = isPaused
                )
            )
        }
    }

    private fun onRideFinished(distance: Double, averageSpeed: Double) {
        Timber.d("Ride finished")

        activeRideId = null
        updateSelectedVehicle(null)

        _uiState.update { uiState ->
            uiState.copy(mapState = uiState.mapState.copy(rideRoute = null), rideState = null)
        }

        viewModelScope.launch {
            _actions.send(
                HomeAction.FinishRide(
                    distance = distance / 1000, // meters to km
                    averageSpeed = averageSpeed
                )
            )
        }
    }

    private fun onError(error: RideEvent.Error) {
        Timber.d(error.message)
        viewModelScope.launch {
            val textResId = when (error) {
                is RideEvent.Error.UserInsideNoParkingZone -> CoreR.string.toast_user_inside_noparking_zone
                is RideEvent.Error.UserTooFarFromVehicle -> CoreR.string.toast_user_too_far
                is RideEvent.Error.NotEnoughFunds -> CoreR.string.toast_not_enough_funds
            }
            _actions.send(HomeAction.Toast(textResId))
        }
        when (error) {
            is RideEvent.Error.NotEnoughFunds -> {
                viewModelScope.launch {
                    delay(OPEN_TOPUP_SCREEN_DELAY_MS)
                    _actions.send(HomeAction.OpenTopUpScreen)
                }
            }

            else -> Unit
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
                            hideMapLoading()
                            vehiclesOnMap = mapContent.availableVehicles
                            updateMapState(
                                vehicleClusterItems = mapContent.availableVehicles.mapToClusterItem(
                                    selectedId = uiState.value.selectedVehicle?.id
                                ),
                                ridingZones = mapContent.ridingZones.map { it.coordinates.mapToLatLng() },
                                noParkingZones = mapContent.noParkingZones.mapToUiModel()
                            )
                        }
                        .onFailure {
                            hideMapLoading()
                            updateMapState(vehicleClusterItems = emptyList())
                        }
                }
                .launchIn(viewModelScope)
        }
    }

    private suspend fun hideMapLoading() {
        mapLoadingJob?.cancelAndJoin()
        mapLoadingJob = viewModelScope.launch {
            delay(LOADING_BAR_DELAY_MS)
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
                val userLocation = uiState.value.mapState.userLocation
                val activeRideId = activeRideId
                val activeRideStartDateTime = activeRideStartDateTime
                if (isInRideMode && userLocation == null && activeRideId != null && activeRideStartDateTime != null) {
                    _actions.send(
                        HomeAction.RestartUserLocation(
                            rideId = activeRideId,
                            startDateTime = activeRideStartDateTime.toString()
                        )
                    )
                }
            }
            return
        }

        userLocationJob = observeUserLocationUseCase()
            .onEach { result ->
                result
                    .onSuccess { userLocation ->
                        val (latitude, longitude) = userLocation.toCoordinates()
                        saveLastMapCameraPositionUseCase(
                            MapCameraPosition(
                                latitude = latitude,
                                longitude = longitude,
                                zoom = DEFAULT_ZOOM
                            )
                        )
                        updateMapState(userLocation = userLocation)
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

    private companion object {
        private const val DEFAULT_ZOOM = 13f
        private const val LOADING_BAR_DELAY_MS = 800L
        private const val OPEN_TOPUP_SCREEN_DELAY_MS = 500L
    }
}
