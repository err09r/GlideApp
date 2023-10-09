package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun DrawerMenuElement(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            tint = Color.Gray,
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = title, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuElementPreview() {
    GlideAppTheme {
        DrawerMenuElement(
            icon = Icons.Rounded.History,
            title = "My Rides",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}
