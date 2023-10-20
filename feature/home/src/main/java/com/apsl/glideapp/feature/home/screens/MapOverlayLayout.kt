@file:Suppress("UnusedReceiverParameter")

package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.LoadingBar
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Gps
import com.apsl.glideapp.core.ui.icons.Menu
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun BoxScope.MapOverlayLayout(
    modifier: Modifier = Modifier,
    height: Dp = 0.dp,
    rideActive: Boolean = false,
    showLoading: Boolean = false,
    onMenuClick: () -> Unit,
    onMyLocationClick: () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp)
        ) {
            if (!rideActive) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = onMenuClick,
                        containerColor = MaterialTheme.colorScheme.surface,
                        elevation = FloatingActionButtonDefaults.loweredElevation()
                    ) {
                        Icon(imageVector = GlideIcons.Menu, contentDescription = null)
                    }

                    if (showLoading) {
                        Spacer(Modifier.width(16.dp))
                        LoadingBar(modifier = Modifier.weight(1f))
                        Spacer(Modifier.width(72.dp))
                    }
                }
            }

            FloatingActionButton(
                onClick = onMyLocationClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(imageVector = GlideIcons.Gps, contentDescription = null)
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun MapOverlayLayoutPreview() {
    GlideAppTheme {
        Box {
            MapOverlayLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding(),
                height = 720.dp,
                showLoading = true,
                onMenuClick = {},
                onMyLocationClick = {}
            )
        }
    }
}
