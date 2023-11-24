package com.apsl.glideapp.feature.rides.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.compress
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.components.RideRoute
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
    val address: String?,
    val route: RideRoute,
    val distance: Int,
    val fare: String,
    val separator: PagingSeparator
)

private val separatorFormatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }

fun Ride.toRideUiModel(): RideUiModel {
    val timeDifference =
        finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())

    val separator = PagingSeparator(
        text = finishDateTime.toJavaLocalDateTime().format(separatorFormatter).capitalized()
    )

    val route = RideRoute(
        route.points
            .compress(25)
            .map { (latitude, longitude) ->
                longitude.toFloat() to if (latitude >= 0) 90f - latitude.toFloat() else latitude.toFloat()
            }
    )

    return RideUiModel(
        id = id,
        startTime = startDateTime.time.toString().substringBeforeLast(':'),
        finishTime = finishDateTime.time.toString().substringBeforeLast(':'),
        address = this.finishAddress ?: this.startAddress,
        route = route,
        distance = this.route.distance.roundToInt(),
        fare = (timeDifference.inWholeMinutes * 3.3).coerceAtLeast(3.3).format(2),
        separator = separator
    )
}
