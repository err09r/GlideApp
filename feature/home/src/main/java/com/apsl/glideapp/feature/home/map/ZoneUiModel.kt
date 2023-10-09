package com.apsl.glideapp.feature.home.map

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.common.models.toCoordinatesBounds
import com.apsl.glideapp.core.model.Zone
import com.apsl.glideapp.core.util.maps.mapToLatLng
import com.apsl.glideapp.core.util.maps.toLatLng
import com.google.android.gms.maps.model.LatLng

@Immutable
data class ZoneUiModel(
    val coordinates: List<LatLng>,
    val center: LatLng
)

fun Zone.toUiModel(): ZoneUiModel {
    return ZoneUiModel(
        coordinates = this.coordinates.mapToLatLng(),
        center = this.coordinates.toCoordinatesBounds().center.toLatLng()
    )
}

fun List<Zone>.mapToUiModel(): List<ZoneUiModel> = this.map(Zone::toUiModel)
