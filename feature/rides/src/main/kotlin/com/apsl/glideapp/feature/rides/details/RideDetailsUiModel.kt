package com.apsl.glideapp.feature.rides.details

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.util.maps.mapToLatLng
import com.google.android.gms.maps.model.LatLng
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Immutable
data class RideDetailsUiModel(
    val startAddress: String?,
    val finishAddress: String?,
    val startDate: String,
    val startTime: String,
    val finishTime: String,
    val route: List<LatLng>,
    val distance: Int,
    val averageSpeed: String,
    val timeInMinutes: Int
)

private val formatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }

fun Ride.toRideDetailsUiModel(): RideDetailsUiModel {
    val timeInMinutes =
        (finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes

    val startTime = startDateTime.time.toString().substringBeforeLast(':')
    val finishTime = finishDateTime.time.toString().substringBeforeLast(':')

    return RideDetailsUiModel(
        startAddress = startAddress,
        finishAddress = finishAddress,
        startDate = startDateTime.toJavaLocalDateTime().format(formatter).capitalized(),
        startTime = startTime,
        finishTime = finishTime,
        route = route.points.mapToLatLng(),
        distance = route.distance.roundToInt(),
        averageSpeed = "${averageSpeed.format(1)} km/h",
        timeInMinutes = timeInMinutes.toInt()
    )
}