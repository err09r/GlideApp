package com.apsl.glideapp.feature.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Battery1Bar
import androidx.compose.material.icons.rounded.Battery2Bar
import androidx.compose.material.icons.rounded.Battery3Bar
import androidx.compose.material.icons.rounded.Battery4Bar
import androidx.compose.material.icons.rounded.Battery5Bar
import androidx.compose.material.icons.rounded.Battery6Bar
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.home.R
import com.apsl.glideapp.feature.home.viewmodels.RideState

@Composable
fun SheetContent(
    vehicleCode: String,
    vehicleRange: Int,
    vehicleCharge: Int,
    modifier: Modifier = Modifier,
    rideState: RideState? = null,
    onStartRideClick: () -> Unit,
    onFinishRideClick: () -> Unit
) {
    AnimatedContent(targetState = rideState, label = "") {
        when (it) {
            RideState.Active -> {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        onClick = {}
                    )
                    {
                        Text(
                            text = "Pause",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        onClick = onFinishRideClick
                    ) {
                        Text(
                            text = "Finish",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            else -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.QrCodeScanner,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = vehicleCode, fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier.rotate(90f),
                                    imageVector = when (vehicleCharge) {
                                        in 90..100 -> Icons.Rounded.BatteryFull
                                        in 75..90 -> Icons.Rounded.Battery6Bar
                                        in 60..75 -> Icons.Rounded.Battery5Bar
                                        in 45..60 -> Icons.Rounded.Battery4Bar
                                        in 30..45 -> Icons.Rounded.Battery3Bar
                                        in 15..30 -> Icons.Rounded.Battery2Bar
                                        else -> Icons.Rounded.Battery1Bar
                                    },
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "$vehicleRange km range", fontSize = 14.sp)
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(R.drawable.ic_scooter),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        onClick = onStartRideClick
                    ) {
                        Text(
                            text = "Start Ride",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SheetContentPreview() {
    GlideAppTheme {
        SheetContent(
            vehicleCode = "0023",
            vehicleRange = 25,
            vehicleCharge = 90,
            rideState = RideState.Active,
            onStartRideClick = {},
            onFinishRideClick = {}
        )
    }
}
