package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.Colors
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
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
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
            rotationGesturesEnabled = true,
            scrollGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = true,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = true
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
                    cameraPositionState.run {
                        animate(
                            CameraUpdateFactory.newLatLngZoom(
                                it.position,
                                position.zoom + 1.0f
                            )
                        )
                    }
                }
                false
            },
            clusterContent = {
                VehicleCluster()
            },
            clusterItemContent = {
                VehicleMarker(selected = it.isSelected, charge = it.charge)
            }
        )

        if (selectedVehicle != null && mapState.selectedVehicleRadius != null) {
            Circle(
                center = selectedVehicle.coordinates,
                fillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                radius = mapState.selectedVehicleRadius,
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp.toPx(),
                visible = cameraPositionState.position.zoom >= MapsConfiguration.VEHICLE_CIRCLE_VISIBILITY_ZOOM_LEVEL
            )
        }

        Polygon(
            points = MapsConfiguration.mapBorders,
            fillColor = Colors.NoRiding.copy(alpha = 0.3f),
            holes = mapState.ridingZones,
            strokeWidth = 2.dp.toPx(),
            strokeJointType = JointType.ROUND,
            strokeColor = Colors.NoRiding
        )

        mapState.noParkingZones.forEach { zone ->
            Polygon(
                points = zone.coordinates,
                fillColor = Colors.NoParking.copy(alpha = 0.3f),
                strokeWidth = 2.dp.toPx(),
                strokeJointType = JointType.ROUND,
                strokeColor = Colors.NoParking
            )
            MarkerComposable(
                state = rememberMarkerState(key = zone.id, position = zone.center),
                visible = cameraPositionState.position.zoom >= MapsConfiguration.NO_PARKING_ZONE_VISIBILITY_ZOOM_LEVEL,
                alpha = 0.8f,
                content = { NoParkingMarker() }
            )
        }

        if (!mapState.rideRoute.isNullOrEmpty()) {
            Polyline(
                points = mapState.rideRoute,
                color = MaterialTheme.colorScheme.primary,
                jointType = JointType.ROUND,
                startCap = RoundCap(),
                endCap = RoundCap(),
                width = 4.dp.toPx()
            )
            StartPointMarker(
                markerState = rememberMarkerState(
                    key = "start",
                    position = mapState.rideRoute.first()
                )
            )
        }
    }
}

@GoogleMapComposable
@Composable
fun StartPointMarker(
    modifier: Modifier = Modifier,
    markerState: MarkerState = rememberMarkerState()
) {
    MarkerComposable(
        state = markerState,
        anchor = Offset(0.5f, 0.5f),
        zIndex = 1f,
        onClick = { true }
    ) {
        Box(
            modifier = modifier
                .size(8.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
    }
}
