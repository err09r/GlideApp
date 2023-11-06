package com.apsl.glideapp.feature.rides.models

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.util.UUID
import com.apsl.glideapp.common.util.capitalized
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.model.Ride
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
    val route: List<Pair<Float, Float>>,
    val distance: Int,
    val fare: String,
    val separator: Separator
)

@Immutable
data class Separator(val id: String = UUID.randomUUID().toString(), val text: String)

private val separatorFormatter by lazy { DateTimeFormatter.ofPattern("EEEE, d MMMM") }

fun Ride.toRideUiModel(): RideUiModel {
    val timeDifference =
        finishDateTime.toInstant(TimeZone.currentSystemDefault()) -
                startDateTime.toInstant(TimeZone.currentSystemDefault())

    val separator = Separator(
        text = finishDateTime.toJavaLocalDateTime().format(separatorFormatter).capitalized()
    )

    return RideUiModel(
        id = id,
        startTime = startDateTime.time.toString().substringBeforeLast(':'),
        finishTime = finishDateTime.time.toString().substringBeforeLast(':'),
        route = route.points
            .compress(40)
            .map { (latitude, longitude) ->
                longitude.toFloat() to if (latitude >= 0) 90f - latitude.toFloat() else latitude.toFloat()
            },
        distance = route.distance.roundToInt(),
        fare = (timeDifference.inWholeMinutes * 3.3).coerceAtLeast(3.3).format(2),
        separator = separator
    )
}

fun <T> List<T>.compress(n: Int): List<T> {
    require(n >= 0) { "Requested n value: $n is less than zero." }
    if (this.size <= 3) {
        return this
    }
    return when (n) {
        0 -> emptyList()
        1 -> this.take(1)
        2 -> listOf(this.first(), this.last())
        this.size -> this
        else -> {
            val ratio = 1f / (n - 2)
            val list = ArrayList<T>(n)
            list.add(this.first())
            this
                .subList(fromIndex = 1, toIndex = this.lastIndex - 1)
                .chunked(size = ((this.size - 2) * ratio).roundToInt().coerceAtLeast(1))
                .forEach { list.add(it.random()) }
            list.add(this.last())
            return list
        }
    }
}
