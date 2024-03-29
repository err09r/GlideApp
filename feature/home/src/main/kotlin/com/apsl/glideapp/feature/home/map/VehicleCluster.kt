package com.apsl.glideapp.feature.home.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun VehicleCluster(modifier: Modifier = Modifier) {
    val outlineColor: Color = MaterialTheme.colorScheme.primary
    val outlineWidth: Dp = 2.dp
    val elevation: Dp = 4.dp

    Surface(
        modifier = modifier,
        shape = CircleShape,
        shadowElevation = elevation,
        tonalElevation = elevation,
        border = BorderStroke(width = outlineWidth, color = outlineColor)
    ) {
        Box(modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = GlideIcons.ElectricScooter,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun VehicleClusterPreview() {
    GlideAppTheme {
        VehicleCluster()
    }
}
