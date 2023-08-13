package com.apsl.glideapp.feature.home.maps

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Immutable
data class VehicleClusterItem(
    val itemPosition: LatLng,
    val id: String,
    val code: String,
    val range: Int,
    val charge: Int,
    val isSelected: Boolean = false
) : ClusterItem {

    override fun getPosition(): LatLng = itemPosition

    override fun getTitle(): String? = null

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null
}
