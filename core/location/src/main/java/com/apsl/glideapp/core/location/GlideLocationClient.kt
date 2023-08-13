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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("MissingPermission")
class GlideLocationClient @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationClient {

    private val scope = CoroutineScope(Job())

    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationUpdateIntervalMs: Long = DEFAULT_LOCATION_REQUEST_INTERVAL_MS

    override fun setLocationUpdateInterval(intervalMillis: Long) {
        this.locationUpdateIntervalMs = intervalMillis
    }

    override val userLocation: Flow<UserLocation>
        get() = callbackFlow {

            if (!context.locationPermissionsGranted) {
                //TODO HANDLE PROPERLY
                throw LocationException("Missing location permission")
            }

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                //TODO HANDLE PROPERLY
                throw LocationException("GPS is disabled")
            }

            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                locationUpdateIntervalMs
            ).build()

            val locationCallback = object : LocationCallback() {

                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.lastLocation?.let { location ->
                        launch {
                            Timber.d("lat: ${location.latitude}/long: ${location.longitude}")
                            send(location.toUserLocation())
                        }
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                Timber.d("closed")
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                locationUpdateIntervalMs = DEFAULT_LOCATION_REQUEST_INTERVAL_MS
            }

        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = LOCATION_FLOW_STOP_TIMEOUT_MS),
            replay = 1
        )

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
        private const val DEFAULT_LOCATION_REQUEST_INTERVAL_MS = 3000L
        private const val LOCATION_FLOW_STOP_TIMEOUT_MS = 5000L
    }
}
