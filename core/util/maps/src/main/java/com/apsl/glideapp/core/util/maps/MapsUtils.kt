@file:Suppress("Unused")

package com.apsl.glideapp.core.util.maps

import android.location.Location
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.model.UserLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

fun Location.toUserLocation(): UserLocation {
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

fun UserLocation.toLocation(): Location {
    return Location(this.provider).apply {
        latitude = latitudeDegrees
        longitude = longitudeDegrees
        provider = provider
        time = timeMs
        elapsedRealtimeNanos = elapsedRealtimeNs
        accuracy = horizontalAccuracyMeters
        altitude = altitudeMeters
        verticalAccuracyMeters = altitudeAccuracyMeters
        speed = speedMetersPerSecond
        speedAccuracyMetersPerSecond = speedAccuracyMetersPerSecond
        bearing = bearingDegrees
        bearingAccuracyDegrees = bearingAccuracyDegrees
    }
}

fun UserLocation.toLatLng() = LatLng(this.latitudeDegrees, this.longitudeDegrees)

fun UserLocation.toCoordinates() = Coordinates(this.latitudeDegrees, this.longitudeDegrees)

fun Coordinates.toLatLng() = LatLng(this.latitude, this.longitude)

fun List<Coordinates>.mapToLatLng() = this.map(Coordinates::toLatLng)

fun List<LatLng>.mapToCoordinates() = this.map(LatLng::toCoordinates)

fun LatLng.toCoordinates() = Coordinates(this.latitude, this.longitude)

fun LatLngBounds.toCoordinatesBounds() = CoordinatesBounds(
    southwest = this.southwest.toCoordinates(),
    northeast = this.northeast.toCoordinates()
)

@JvmName("listToLatLngBounds")
fun List<LatLng>.toLatLngBounds(): LatLngBounds {
    val topmostLatitude = this.maxOf { it.latitude }
    val bottommostLatitude = this.maxOf { it.latitude }
    val leftmostLongitude = this.maxOf { it.longitude }
    val rightmostLongitude = this.maxOf { it.longitude }

    val southwest = LatLng(bottommostLatitude, leftmostLongitude)
    val northeast = LatLng(topmostLatitude, rightmostLongitude)

    return LatLngBounds(southwest, northeast)
}

@JvmName("coordinatesToLatLngBounds")
fun List<Coordinates>.toLatLngBounds(): LatLngBounds {
    return this.map(Coordinates::toLatLng).toLatLngBounds()
}
