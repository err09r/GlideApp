package com.apsl.glideapp.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.location.LocationListenerCompat
import com.apsl.glideapp.common.util.asResult
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.location.GpsDisabledException
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.domain.location.MissingLocationPermissionsException
import com.apsl.glideapp.core.model.UserLocation
import com.apsl.glideapp.core.util.android.locationPermissionsGranted
import com.apsl.glideapp.core.util.maps.toUserLocation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@SuppressLint("MissingPermission")
class LocationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appDataStore: AppDataStore
) : LocationClient {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val _userLocation = MutableSharedFlow<Flow<Result<UserLocation>>>()
    override val userLocation = _userLocation
        .onStart {
            Timber.d("User Location flow started")
            val isRideModeActive = appDataStore.isRideModeActive.firstOrNull() ?: false
            val interval = getLocationUpdateInterval(isRideModeActive = isRideModeActive)
            emit(buildUserLocationFlow(locationUpdateIntervalMs = interval))
        }
        .onCompletion { Timber.d("User Location flow completed") }
        .flatMapLatest { it }
        .shareIn(scope = scope, started = SharingStarted.WhileSubscribed())

    init {
        observeLocationUpdateInterval()
    }

    private fun observeLocationUpdateInterval() {
        scope.launch {
            appDataStore.isRideModeActive
                .map { it ?: false }
                .distinctUntilChanged()
                .collectLatest { isRideModeActive ->
                    val interval = getLocationUpdateInterval(isRideModeActive = isRideModeActive)
                    Timber.d("isRideModeActive: $isRideModeActive, interval: $interval")
                    _userLocation.emit(
                        buildUserLocationFlow(locationUpdateIntervalMs = interval)
                    )
                }
        }
    }

    private fun getLocationUpdateInterval(isRideModeActive: Boolean): Long {
        return if (isRideModeActive) {
            LocationClient.RIDE_MODE_LOCATION_REQUEST_INTERVAL_MS
        } else {
            LocationClient.DEFAULT_LOCATION_REQUEST_INTERVAL_MS
        }
    }

    private fun buildUserLocationFlow(locationUpdateIntervalMs: Long) = callbackFlow {
        ensureLocationPermissionsGranted()
        ensureProvidersEnabled()

        val locationListener = LocationListenerCompat { location ->
            val userLocation = location.toUserLocation()
            scope.launch {
                Timber.d("Latitude: ${userLocation.latitudeDegrees}, Longitude: ${userLocation.longitudeDegrees}")
                send(userLocation)
            }
        }

        withContext(Dispatchers.Main.immediate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val locationRequest = LocationRequest.Builder(locationUpdateIntervalMs)
                    .setMinUpdateIntervalMillis(locationUpdateIntervalMs)
                    .setQuality(LocationRequest.QUALITY_HIGH_ACCURACY)
                    .setMinUpdateDistanceMeters(LOCATION_UPDATE_MIN_DISTANCE)
                    .build()

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    locationRequest,
                    ContextCompat.getMainExecutor(context),
                    locationListener
                )
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    locationUpdateIntervalMs,
                    LOCATION_UPDATE_MIN_DISTANCE,
                    locationListener
                )
            }
        }

        awaitClose {
            Timber.d("Location subflow has been either cancelled or closed")
            locationManager.removeUpdates(locationListener)
        }
    }.asResult()

    private fun ensureLocationPermissionsGranted() {
        if (!context.locationPermissionsGranted) {
            throw MissingLocationPermissionsException()
        }
    }

    private fun ensureProvidersEnabled() {
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            throw GpsDisabledException()
        }
    }

    override val isGpsEnabled: Boolean
        get() = try {
            ensureProvidersEnabled()
            true
        } catch (e: Exception) {
            false
        }

    private companion object {
        private const val LOCATION_UPDATE_MIN_DISTANCE = 0.8f
    }
}
