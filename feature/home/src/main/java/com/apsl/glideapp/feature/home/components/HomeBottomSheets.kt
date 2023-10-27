package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.Battery
import com.apsl.glideapp.core.ui.icons.BatteryFull
import com.apsl.glideapp.core.ui.icons.BatteryLow
import com.apsl.glideapp.core.ui.icons.BatteryMedium
import com.apsl.glideapp.core.ui.icons.Bell
import com.apsl.glideapp.core.ui.icons.Card
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route2
import com.apsl.glideapp.core.ui.icons.Warning
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.home.R
import com.apsl.glideapp.feature.home.screens.BatteryState
import com.apsl.glideapp.feature.home.screens.SelectedVehicleUiModel
import com.apsl.glideapp.feature.home.screens.VehicleUiModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun ActiveRideSheetLayout(
    vehicle: VehicleUiModel,
    modifier: Modifier = Modifier,
    onFinishRideClick: () -> Unit
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            // Top padding is 32.dp because of RectangleShape
            .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Scooter ${vehicle.code}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))
                ScooterInfoComponent(
                    imageVector = GlideIcons.Battery,
                    text = "${vehicle.batteryCharge}% battery"
                )
                Spacer(Modifier.height(4.dp))
                ScooterInfoComponent(
                    imageVector = GlideIcons.Route2,
                    text = "${vehicle.range} km range"
                )
            }
            Column {
                FilledTonalIconButton(onClick = {}) {
                    Icon(imageVector = GlideIcons.Bell, contentDescription = null)
                }
                Spacer(Modifier.height(8.dp))
                FilledTonalIconButton(onClick = {}) {
                    Icon(imageVector = GlideIcons.Warning, contentDescription = null)
                }
            }
        }
        Spacer(Modifier.height(32.dp))
        Row {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Pause")
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = onFinishRideClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@Composable
fun DefaultSheetLayout(
    selectedVehicle: SelectedVehicleUiModel,
    modifier: Modifier = Modifier,
    onStartRideClick: () -> Unit
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Scooter ${selectedVehicle.code}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(20.dp))
                ScooterInfoComponent(
                    imageVector = when (selectedVehicle.batteryState) {
                        BatteryState.Low -> GlideIcons.BatteryLow
                        BatteryState.Medium -> GlideIcons.BatteryMedium
                        BatteryState.Full -> GlideIcons.BatteryFull
                        BatteryState.Undefined -> GlideIcons.Battery
                    },
                    text = "${selectedVehicle.range} km range"
                )
                Spacer(Modifier.height(4.dp))
                ScooterInfoComponent(
                    imageVector = GlideIcons.Card,
                    text = "${selectedVehicle.unlockingFee} zł to start, then ${selectedVehicle.farePerMinute} zł/min"
                )
            }
            Image(
                painter = painterResource(R.drawable.img_scooter),
                contentDescription = null,
                modifier = Modifier.size(88.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTonalButton(onClick = {}) {
                Icon(imageVector = GlideIcons.Bell, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(text = "Ring")
            }
            FilledTonalButton(onClick = {}) {
                Icon(imageVector = GlideIcons.Warning, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(text = "Report issue")
            }
        }
        Spacer(Modifier.height(48.dp))
        Button(
            onClick = onStartRideClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Start Ride")
        }
    }
}

@Composable
private fun ScooterInfoComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultSheetLayoutPreview() {
    GlideAppTheme {
        DefaultSheetLayout(
            selectedVehicle = SelectedVehicleUiModel(
                id = "123",
                code = "0023",
                range = 7,
                unlockingFee = 3.3,
                farePerMinute = 0.85,
                coordinates = LatLng(0.0, 0.0),
                batteryCharge = 30,
                batteryState = BatteryState.Medium
            ),
            onStartRideClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveRideSheetLayoutPreview() {
    GlideAppTheme {
        ActiveRideSheetLayout(
            vehicle = VehicleUiModel(
                id = "123",
                code = "0023",
                range = 7,
                unlockingFee = 3.3,
                farePerMinute = 0.85,
                batteryCharge = 30,
                batteryState = BatteryState.Medium
            ),
            onFinishRideClick = {}
        )
    }
}
