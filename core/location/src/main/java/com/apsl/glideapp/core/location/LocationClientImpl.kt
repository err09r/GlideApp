package com.apsl.glideapp.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.apsl.glideapp.core.domain.LocationException
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.domain.location.UserLocation
import com.apsl.glideapp.core.util.locationPermissionsGranted
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("MissingPermission")
class LocationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationClient {

    private val scope = CoroutineScope(SupervisorJob())

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _userLocation = MutableSharedFlow<UserLocation>()
    override val userLocation = _userLocation.asSharedFlow()

    private var locationUpdatesJob: Job? = null

    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null

    override suspend fun startReceivingLocationUpdates(locationUpdateIntervalMs: Long) {
        Timber.d("startReceivingLocationUpdates, job: ${locationUpdatesJob.toString()}")
        locationUpdatesJob?.cancel()
        locationUpdatesJob = callbackFlow<Unit> {
            ensureLocationPermissionsGranted()
            ensureProvidersEnabled()

            locationRequest = LocationRequest
                .Builder(USER_LOCATION_PRIORITY, locationUpdateIntervalMs)
                .build()

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        scope.launch {
                            Timber.d("lat: ${location.latitude}/long: ${location.longitude}")
                            _userLocation.emit(location.toUserLocation())
                        }
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                checkNotNull(locationRequest),
                checkNotNull(locationCallback),
                Looper.getMainLooper()
            )

            awaitClose {
                val callback = locationCallback
                if (callback != null) {
                    fusedLocationProviderClient.removeLocationUpdates(callback)
                    locationCallback = null
                }
                locationRequest = null
            }
        }
            .catch { Timber.d(it) }
            .launchIn(scope)
    }

    override suspend fun stopReceivingLocationUpdates() {
        if (locationRequest?.intervalMillis == LocationClient.DEFAULT_LOCATION_REQUEST_INTERVAL_MS) {
            locationUpdatesJob?.cancelAndJoin()
        }
    }

    private fun ensureLocationPermissionsGranted() {
        if (!context.locationPermissionsGranted) {
            //TODO HANDLE PROPERLY
            throw LocationException("Missing location permission")
        }
    }

    private fun ensureProvidersEnabled() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            //TODO HANDLE PROPERLY
            throw LocationException("GPS is disabled")
        }
    }

    private fun Location.toUserLocation(): UserLocation {
        return UserLocation(
            provider = provider,
            timeMs = time,
            elapsedRealtimeNs = elapsedRealtimeNanos,
            latitudeDegrees = latitude,
            longitudeDegrees = longitude,
            horizontalAccuracyMeters = accuracy,
            altitudeMeters = altitude,
            altitudeAccuracyMeters = verticalAccuracyMeters,
            speedMetersPerSecond = speed,
            speedAccuracyMetersPerSecond = speedAccuracyMetersPerSecond,
            bearingDegrees = bearing,
            bearingAccuracyDegrees = bearingAccuracyDegrees
        )
    }

    private companion object {
        private const val USER_LOCATION_PRIORITY = Priority.PRIORITY_HIGH_ACCURACY
    }
}
