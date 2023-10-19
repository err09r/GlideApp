package com.apsl.glideapp.feature.home.map

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.util.maps.toLatLng
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Immutable
data class VehicleClusterItem(
    val id: String,
    val itemPosition: LatLng,
    val charge: Float
) : ClusterItem {

    override fun getPosition(): LatLng = itemPosition

    override fun getTitle(): String? = null

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null
}

fun Vehicle.toClusterItem(): VehicleClusterItem {
    return VehicleClusterItem(
        id = this.id,
        itemPosition = this.coordinates.toLatLng(),
        charge = this.batteryCharge.toFloat()
    )
}

fun List<Vehicle>.mapToClusterItem(): List<VehicleClusterItem> {
    return this.map(Vehicle::toClusterItem)
}
