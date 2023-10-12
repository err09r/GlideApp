package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun DrawerStatsComponent(icon: ImageVector, value: Int, units: String) {
    Column {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(text = value.toString(), fontSize = 24.sp)
        Text(text = units)
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerStatsComponentPreview() {
    GlideAppTheme {
        DrawerStatsComponent(icon = GlideIcons.Route, value = 0, units = "Rides")
    }
}
