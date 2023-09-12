package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ElectricScooter
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun DrawerContent(
    username: String?,
    userTotalDistance: Int,
    userTotalRides: Int,
    onRefreshData: () -> Unit,
    onMyRidesClick: () -> Unit,
    onWalletClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    icon = Icons.Rounded.Route,
                    value = userTotalDistance,
                    units = "meters"
                )
                DrawerStatsComponent(
                    icon = Icons.Rounded.ElectricScooter,
                    value = userTotalRides,
                    units = "rides"
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Divider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            DrawerMenuElement(
                icon = Icons.Rounded.Wallet,
                title = "Wallet",
                onClick = onWalletClick
            )
            DrawerMenuElement(
                icon = Icons.Rounded.History,
                title = "My Rides",
                onClick = onMyRidesClick
            )
            DrawerMenuElement(icon = Icons.Rounded.Help, title = "Help", onClick = {})
            DrawerMenuElement(icon = Icons.Rounded.Settings, title = "Settings", onClick = {})
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(thickness = 4.dp)
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
            onRefreshData = {},
            onMyRidesClick = {},
            onWalletClick = {}
        )
    }
}
