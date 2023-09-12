package com.apsl.glideapp.feature.home.maps

import android.location.Location
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.CoordinatesBounds
import com.apsl.glideapp.core.domain.location.UserLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

fun UserLocation.toLocation(): Location {
    return Location(this.provider).apply {
        latitude = this@toLocation.latitudeDegrees
        longitude = this@toLocation.longitudeDegrees
        provider = this@toLocation.provider
        time = this@toLocation.timeMs
        elapsedRealtimeNanos = this@toLocation.elapsedRealtimeNs
        accuracy = this@toLocation.horizontalAccuracyMeters
        altitude = this@toLocation.altitudeMeters
        verticalAccuracyMeters = this@toLocation.altitudeAccuracyMeters
        speed = this@toLocation.speedMetersPerSecond
        speedAccuracyMetersPerSecond = this@toLocation.speedAccuracyMetersPerSecond
        bearing = this@toLocation.bearingDegrees
        bearingAccuracyDegrees = this@toLocation.bearingAccuracyDegrees
    }
}

fun UserLocation.toLatLng() = LatLng(this.latitudeDegrees, this.longitudeDegrees)

fun UserLocation.toCoordinates() = Coordinates(this.latitudeDegrees, this.longitudeDegrees)

fun Coordinates.toLatLng() = LatLng(this.latitude, this.longitude)

fun List<Coordinates>.toNoParkingZones() = NoParkingZone(this.map(Coordinates::toLatLng))

fun LatLng.toCoordinates() = Coordinates(this.latitude, this.longitude)

fun LatLngBounds.toCoordinatesBounds() = CoordinatesBounds(
    southwest = this.southwest.toCoordinates(),
    northeast = this.northeast.toCoordinates()
)

fun List<LatLng>.toLatLngBounds(): LatLngBounds {
    val topmostPoint = this.maxBy { it.latitude }
    val bottommostPoint = this.minBy { it.latitude }
    val leftmostPoint = this.minBy { it.longitude }
    val rightmostPoint = this.maxBy { it.longitude }

    val southwest = LatLng(bottommostPoint.latitude, leftmostPoint.longitude)
    val northeast = LatLng(topmostPoint.latitude, rightmostPoint.longitude)

    return LatLngBounds(southwest, northeast)
}
