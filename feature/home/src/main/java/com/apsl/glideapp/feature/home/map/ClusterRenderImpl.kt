package com.apsl.glideapp.feature.home.map

import android.content.Context
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

@Deprecated("Use 'Clustering' composable as temporally solution instead.")
class ClusterRendererImpl(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<VehicleClusterItem>
) : DefaultClusterRenderer<VehicleClusterItem>(context, map, clusterManager) {

    private val markerView = ImageView(context).apply {
//        setPadding(2.asDp)
//        background = ContextCompat.getDrawable(context, R.drawable.bg_marker_vehicle)
//        setImageDrawable(
//            ContextCompat.getDrawable(context, R.drawable.ic_electric_scooter)
//        )
    }

    private val clusterView = ImageView(context).apply {
//        setPadding(4.asDp)
//        background = ContextCompat.getDrawable(context, R.drawable.bg_marker_vehicle)
//        setImageDrawable(
//            ContextCompat.getDrawable(context, R.drawable.ic_electric_scooter)
//        )
    }

    private val selectedMarkerView = ImageView(context).apply {
//        setPadding(2.asDp)
//        background = ContextCompat.getDrawable(context, R.drawable.bg_marker_vehicle_selected)
//        setImageDrawable(
//            ContextCompat.getDrawable(context, R.drawable.ic_electric_scooter)
//        )
//        DrawableCompat.setTint(drawable, Color.WHITE)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<VehicleClusterItem>): Boolean {
        return cluster.size > 2
    }

    override fun onBeforeClusterItemRendered(
        item: VehicleClusterItem,
        markerOptions: MarkerOptions
    ) {
//        markerOptions.icon(if (item.isSelected) selectedMarketIcon else markerIcon)
    }

    override fun onClusterItemUpdated(item: VehicleClusterItem, marker: Marker) {
//        marker.setIcon(if (item.isSelected) selectedMarketIcon else markerIcon)
    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<VehicleClusterItem>,
        markerOptions: MarkerOptions
    ) {
//        markerOptions.icon(clusterIcon)
    }

    override fun onClusterUpdated(cluster: Cluster<VehicleClusterItem>, marker: Marker) {
//        marker.setIcon(clusterIcon)
    }

//    private val markerIcon: BitmapDescriptor =
//        BitmapDescriptorFactory.fromBitmap(markerView.toBitmap(26.asDp, 26.asDp))
//
//    // Customize your ClusterView. The cluster gives you its size (cluster.size) and its items within it (cluster.items)
//    // Use that to customize the cluster appearance.
//    private val selectedMarketIcon: BitmapDescriptor =
//        BitmapDescriptorFactory.fromBitmap(selectedMarkerView.toBitmap(26.asDp, 26.asDp))
//
//    // Customize your ClusterView. The cluster gives you its size (cluster.size) and its items within it (cluster.items)
//    // Use that to customize the cluster appearance.
//    private val clusterIcon: BitmapDescriptor =
//        BitmapDescriptorFactory.fromBitmap(clusterView.toBitmap(36.asDp, 36.asDp))
}