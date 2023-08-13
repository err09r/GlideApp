package com.apsl.glideapp.feature.rides.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.RoundedCornerShape
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.rides.models.RideDetailsUiModel
import com.apsl.glideapp.feature.rides.viewmodels.RideDetailsUiState
import com.apsl.glideapp.feature.rides.viewmodels.RideDetailsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun RideDetailsScreen(
    rideId: String,
    viewModel: RideDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getRideById(rideId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RideDetailsScreenContent(uiState = uiState, onBackClick = onNavigateBack)
}

@Composable
fun RideDetailsScreenContent(uiState: RideDetailsUiState, onBackClick: () -> Unit) {
    val context = LocalContext.current

    val scaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Expanded)
        )

    val cameraPositionState = remember(uiState.mapCameraBounds.center) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(uiState.mapCameraBounds.center, 13f)
        )
    }

    BottomSheetScaffold(
        sheetContent = {
            uiState.ride?.let { ride ->
                Column(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    ride.startAddress?.let {
                        Text(text = "Started at: $it")
                    }

                    ride.finishAddress?.let {
                        Text(text = "Finished at: $it")
                    }

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = ride.fromTimeToTime)
                        ride.timeInMinutes?.let {
                            Text(text = "Ride lasted $it minutes")
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(text = "Total distance travelled: ${ride.distance} m")
                    Text(text = "Average speed: ${ride.averageSpeed}")
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = false,
        sheetShape = RoundedCornerShape(top = 16.dp),
        sheetElevation = 8.dp
    ) {
        FeatureScreen(
            topBarText = "Ride Details",
            onBackClick = onBackClick
        ) {
            GoogleMap(
                modifier = Modifier,
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    latLngBoundsForCameraTarget = uiState.mapCameraBounds,
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        context,
                        CoreR.raw.map_style
                    ),
                    maxZoomPreference = 20f,
                    minZoomPreference = 11f
                ),
                uiSettings = MapUiSettings(
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
                ),
                onMapClick = { }
            ) {
                if (uiState.ride != null) {
                    Polyline(points = uiState.ride.route)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RideDetailsScreenPreview() {
    GlideAppTheme {
        RideDetailsScreenContent(
            uiState = RideDetailsUiState(
                ride = RideDetailsUiModel(
                    startAddress = "ul. Konarskiego 5A",
                    finishAddress = "ul. Wolska 75",
                    fromTimeToTime = "16:24 - 16:56",
                    route = emptyList(),
                    distance = 4154,
                    averageSpeed = "15.3 km/h",
                    timeInMinutes = 32
                )
            ),
            onBackClick = {}
        )
    }
}
