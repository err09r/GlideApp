package com.apsl.glideapp.feature.home.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.core.content.ContextCompat
import com.apsl.glideapp.core.model.UserLocation
import com.apsl.glideapp.core.util.maps.toLocation
import com.apsl.glideapp.feature.home.R
import com.apsl.glideapp.feature.home.maps.ClusterRendererImpl
import com.apsl.glideapp.feature.home.maps.HomeLocationSource
import com.apsl.glideapp.feature.home.maps.NoParkingZone
import com.apsl.glideapp.feature.home.maps.VehicleClusterItem
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import timber.log.Timber
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun GlideMap(
    cameraPositionState: CameraPositionState,
    vehicleClusterItems: List<VehicleClusterItem>,
    ridingZones: List<List<LatLng>>,
    noParkingZones: List<NoParkingZone>,
    userLocation: UserLocation?,
    mapPaddingBottom: Dp,
    modifier: Modifier = Modifier,
    rideRoute: List<LatLng>? = null,
    onLoadMapDataWithinBounds: (LatLngBounds) -> Unit,
    onVehicleSelect: (VehicleClusterItem?) -> Unit
) {
    val context = LocalContext.current

    var clusterManager by remember {
        mutableStateOf<ClusterManager<VehicleClusterItem>?>(null)
    }

    LaunchedEffect(userLocation) {
        userLocation?.let {
            HomeLocationSource.onLocationChanged(it.toLocation())
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            latLngBoundsForCameraTarget = LatLngBounds(LatLng(48.45, 13.9), LatLng(55.75, 23.15)),
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, CoreR.raw.map_style),
            maxZoomPreference = 20f,
            minZoomPreference = 9f//11f
        ),
        locationSource = HomeLocationSource,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false
        ),
        onMapClick = { onVehicleSelect(null) },
        contentPadding = PaddingValues(bottom = mapPaddingBottom)
    ) {
        MapEffect(vehicleClusterItems) { map ->

            map.setOnCameraIdleListener {
                val visibleBounds =
                    cameraPositionState.projection?.visibleRegion?.latLngBounds
                if (visibleBounds != null) {
                    onLoadMapDataWithinBounds(visibleBounds)
                }
            }

            if (clusterManager == null) {
                clusterManager = ClusterManager<VehicleClusterItem>(context, map).apply {
                    renderer = ClusterRendererImpl(context, map, this)

                    setOnClusterItemClickListener {
                        onVehicleSelect(it)
                        false
                    }

                    setOnClusterClickListener {
//                        scope.launch {
//                            cameraPositionState.animate(CameraUpdateFactory.zoomIn())
//                        }
                        false
                    }
                }
            }
            clusterManager?.run {
                Timber.d("triggered")
                clearItems()
                addItems(vehicleClusterItems.filter {
                    cameraPositionState.projection?.visibleRegion?.latLngBounds?.contains(
                        it.position
                    ) ?: false
                })
                cluster()
            }
        }

        Polygon(
            points = listOf(
                LatLng(90.0, 0.0),
                LatLng(90.0, 90.0),
                LatLng(-90.0, 90.0),
                LatLng(-90.0, 0.0)
            ),
            fillColor = Color(15, 49, 119, 80),
            holes = ridingZones,
            strokeWidth = 0f
        )

        noParkingZones.forEach { zone ->
            Polygon(
                points = zone.coordinates,
                fillColor = Color(196, 45, 45, 85),
                strokeWidth = 1f
            )
            val icon = remember {
                bitmapDescriptor(context, R.drawable.ic_no_parking)
            }
            Marker(
                state = MarkerState(position = zone.center),
                icon = icon,
                visible = cameraPositionState.position.zoom >= 14f
            )
        }

        if (!rideRoute.isNullOrEmpty()) {
            Polyline(
                points = rideRoute,
                jointType = JointType.ROUND,
                startCap = RoundCap() //CustomCap(a)
            )
        }
    }
}

fun bitmapDescriptor(
    context: Context,
    @DrawableRes drawableResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, drawableResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
