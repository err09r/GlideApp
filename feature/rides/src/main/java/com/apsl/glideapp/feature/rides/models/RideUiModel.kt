package com.apsl.glideapp.feature.rides.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.ride.Ride
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime

@Immutable
data class RideUiModel(
    val id: String,
    val startTime: String,
    val finishTime: String,
    val distance: Int,
    val fare: String,
    val separatorText: String
)

private val separatorFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")

fun Ride.toRideUiModel(): RideUiModel {
    val timeDifference =
        finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())

    return RideUiModel(
        id = id,
        startTime = startDateTime.time.toString().substringBeforeLast(':'),
        finishTime = finishDateTime.time.toString().substringBeforeLast(':'),
        distance = distance.roundToInt(),
        fare = (timeDifference.inWholeMinutes * 3.3).coerceAtLeast(3.3).format(2),
        separatorText = finishDateTime.toJavaLocalDateTime().format(separatorFormatter)
    )
}
