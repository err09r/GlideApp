package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.toDp
import com.apsl.glideapp.core.ui.toPx
import com.apsl.glideapp.core.util.maps.MapsConfiguration
import com.apsl.glideapp.feature.home.components.NoParkingMarker
import com.apsl.glideapp.feature.home.components.VehicleCluster
import com.apsl.glideapp.feature.home.components.VehicleMarker
import com.apsl.glideapp.feature.home.map.HomeLocationSource
import com.apsl.glideapp.feature.home.map.MapState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun GlideMap(
    cameraPositionState: CameraPositionState,
    mapState: MapState,
    modifier: Modifier = Modifier,
    selectedVehicle: SelectedVehicleUiModel? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onVehicleSelect: (String?) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(mapState.userLocation) {
        mapState.userLocation?.let { HomeLocationSource.onLocationChanged(it) }
    }

    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true,
            latLngBoundsForCameraTarget = MapsConfiguration.homeCameraBounds,
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, CoreR.raw.map_style),
            maxZoomPreference = 20f,
            minZoomPreference = 9f//11f
        )
    }

    val mapUiSettings = remember {
        MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        locationSource = HomeLocationSource,
        uiSettings = mapUiSettings,
        onMapClick = remember { { onVehicleSelect(null) } },
        contentPadding = contentPadding
    ) {
        Clustering(
            items = mapState.vehicleClusterItems,
            onClusterItemClick = {
                onVehicleSelect(it.id)
                false
            },
            onClusterClick = {
                scope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomIn())
                }
                false
            },
            clusterContent = {
                VehicleCluster()
            },
            clusterItemContent = {
                VehicleMarker(
                    selected = it.id == selectedVehicle?.id,
                    charge = it.charge
                )
            }
        )

        if (selectedVehicle != null && mapState.selectedVehicleRadius != null) {
            Circle(
                center = selectedVehicle.coordinates,
                fillColor = Color.Red.copy(alpha = 0.2f),
                radius = mapState.selectedVehicleRadius,
                strokeColor = Color.Black,
                strokeWidth = 2.toDp().toPx(),
                visible = cameraPositionState.position.zoom >= MapsConfiguration.VEHICLE_CIRCLE_VISIBILITY_ZOOM_LEVEL
            )
        }

        Polygon(
            points = MapsConfiguration.mapBorders,
            fillColor = Color(15, 49, 119, 80),
            holes = mapState.ridingZones,
            strokeWidth = 0f
        )

        mapState.noParkingZones.forEach { zone ->
            Polygon(
                points = zone.coordinates,
                fillColor = Color(196, 45, 45, 85),
                strokeWidth = 0f
            )
            MarkerComposable(
                state = rememberMarkerState(key = zone.id, position = zone.center),
                visible = cameraPositionState.position.zoom >= MapsConfiguration.NO_PARKING_ZONE_VISIBILITY_ZOOM_LEVEL,
                alpha = 0.85f,
                content = { NoParkingMarker() }
            )
        }

        if (!mapState.rideRoute.isNullOrEmpty()) {
            Polyline(
                points = mapState.rideRoute,
                jointType = JointType.ROUND,
                startCap = RoundCap() //CustomCap(a)
            )
        }
    }
}
