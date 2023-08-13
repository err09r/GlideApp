package com.apsl.glideapp.feature.rides.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.ride.Ride
import com.google.android.gms.maps.model.LatLng
import kotlin.math.roundToInt
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Immutable
data class RideDetailsUiModel(
    val startAddress: String?,
    val finishAddress: String?,
    val fromTimeToTime: String,
    val route: List<LatLng>,
    val distance: Int,
    val averageSpeed: String,
    val timeInMinutes: Int?
)

//TODO: MOVE TO COMMON
fun Coordinates.toLatLng() = LatLng(this.latitude, this.longitude)

fun Ride.toRideDetailsUiModel(): RideDetailsUiModel {
    val timeInMinutes =
        (finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes

    val startTime = startDateTime.time.toString().substringBeforeLast(':')
    val finishTime = finishDateTime.time.toString().substringBeforeLast(':')

    return RideDetailsUiModel(
        startAddress = startAddress,
        finishAddress = finishAddress,
        fromTimeToTime = "$startTime - $finishTime",
        route = route.map(Coordinates::toLatLng),
        distance = distance.roundToInt(),
        averageSpeed = "${averageSpeed.format(1)} km/h",
        timeInMinutes = if (timeInMinutes > 1) timeInMinutes.toInt() else null
    )
}
