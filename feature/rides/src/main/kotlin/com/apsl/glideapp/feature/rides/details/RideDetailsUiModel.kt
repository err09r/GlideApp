package com.apsl.glideapp.feature.rides.details

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.util.android.DistanceFormatter
import com.apsl.glideapp.core.util.maps.mapToLatLng
import com.google.android.gms.maps.model.LatLng
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Immutable
data class RideDetailsUiModel(
    val startAddress: String?,
    val finishAddress: String?,
    val startDate: String,
    val startTime: String,
    val finishTime: String,
    val route: List<LatLng>,
    val distanceMeters: String,
    val averageSpeedKmh: String,
    val timeInMinutes: String
)

private val dateFormatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }
private val hoursMinutesFormatter by lazy { DateTimeFormatter.ofPattern("HH:mm") }

fun Ride.toRideDetailsUiModel(): RideDetailsUiModel {
    val timeInMinutes =
        (finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes

    return RideDetailsUiModel(
        startAddress = startAddress,
        finishAddress = finishAddress,
        startDate = startDateTime.format(dateFormatter).capitalized(),
        startTime = startDateTime.format(hoursMinutesFormatter),
        finishTime = finishDateTime.format(hoursMinutesFormatter),
        route = route.points.mapToLatLng(),
        distanceMeters = DistanceFormatter.format(route.distance.roundToInt()),
        averageSpeedKmh = DistanceFormatter.format(averageSpeedKmh),
        timeInMinutes = timeInMinutes.toInt().toString()
    )
}
