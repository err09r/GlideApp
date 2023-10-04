package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Help
import com.apsl.glideapp.core.ui.icons.MyRides
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.icons.Settings
import com.apsl.glideapp.core.ui.icons.Wallet
import com.apsl.glideapp.core.ui.rippleClickable
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Immutable
data class DrawerMenuItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)

@Composable
fun DrawerContent(
    username: String?,
    userTotalDistance: Int,
    userTotalRides: Int,
    modifier: Modifier = Modifier,
    onMyRidesClick: () -> Unit,
    onWalletClick: () -> Unit
) {
    val items = remember {
        listOf(
            DrawerMenuItem(
                icon = GlideIcons.Wallet,
                title = "Wallet",
                onClick = onWalletClick
            ),
            DrawerMenuItem(
                icon = GlideIcons.MyRides,
                title = "My Rides",
                onClick = onMyRidesClick
            ),
            DrawerMenuItem(
                icon = GlideIcons.Help,
                title = "Help",
                onClick = {}
            ),
            DrawerMenuItem(
                icon = GlideIcons.Settings,
                title = "Settings",
                onClick = {}
            )
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(vertical = 16.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            if (username != null) {
                Text(text = "Hi, $username", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                DrawerStatsComponent(
                    icon = GlideIcons.Route,
                    value = userTotalDistance,
                    units = "meters"
                )
                DrawerStatsComponent(
                    icon = GlideIcons.ElectricScooter,
                    value = userTotalRides,
                    units = "rides"
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Column {
            items.forEachIndexed { index, item ->
                if (index == 0) {
                    Divider(thickness = 2.dp)
                }
                Row(
                    modifier = Modifier
                        .rippleClickable(onClick = item.onClick)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Icon(imageVector = item.icon, contentDescription = "")
                    Spacer(Modifier.width(16.dp))
                    Text(text = item.title)
                }
                if (index == items.lastIndex) {
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    GlideAppTheme {
        DrawerContent(
            username = "err09r",
            userTotalDistance = 1405,
            userTotalRides = 23,
            onMyRidesClick = {},
            onWalletClick = {}
        )
    }
}
