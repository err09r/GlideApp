package com.apsl.glideapp.feature.home.map

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.Vehicle
import com.apsl.glideapp.core.util.maps.toLatLng
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Immutable
data class VehicleClusterItem(
    val id: String,
    private val itemPosition: LatLng,
    val isSelected: Boolean = false,
    val charge: Float
) : ClusterItem {

    override fun getPosition(): LatLng = itemPosition

    override fun getTitle(): String? = null

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null
}

fun Vehicle.toClusterItem(isSelected: Boolean): VehicleClusterItem {
    return VehicleClusterItem(
        id = this.id,
        itemPosition = this.coordinates.toLatLng(),
        isSelected = isSelected,
        charge = this.batteryCharge.toFloat()
    )
}

fun List<Vehicle>.mapToClusterItem(selectedId: String?): List<VehicleClusterItem> {
    return this.map { it.toClusterItem(isSelected = selectedId == it.id) }
}
