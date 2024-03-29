package com.apsl.glideapp.feature.rides.allrides

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Ride
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.util.android.CurrencyFormatter
import com.apsl.glideapp.core.util.android.NumberFormatter
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Immutable
data class RideUiModel(
    val id: String,
    val startTime: String,
    val finishTime: String,
    val address: String?,
    val route: RideRoute,
    val distanceMeters: String,
    val fare: String,
    val separator: PagingSeparator
)

@Immutable
//@JvmInline
// Normal class instead of inline, for support in Preview
data class RideRoute(val value: List<Pair<Float, Float>>)

private val separatorFormatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }
private val hoursMinutesFormatter by lazy { DateTimeFormatter.ofPattern("HH:mm") }

fun Ride.toRideUiModel(): RideUiModel {
    val timeDifference =
        finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())

    val separator = PagingSeparator(text = finishDateTime.format(separatorFormatter).capitalized())

    val route = RideRoute(
        route.points
            // .compress(25)
            .map { (latitude, longitude) ->
                longitude.toFloat() to if (latitude >= 0) 90f - latitude.toFloat() else latitude.toFloat()
            }
    )

    return RideUiModel(
        id = id,
        startTime = startDateTime.format(hoursMinutesFormatter),
        finishTime = finishDateTime.format(hoursMinutesFormatter),
        address = this.finishAddress ?: this.startAddress,
        route = route,
        distanceMeters = NumberFormatter.format(this.route.distance.roundToInt()),
        fare = CurrencyFormatter.format((timeDifference.inWholeMinutes * 3.3).coerceAtLeast(3.3)),
        separator = separator
    )
}
