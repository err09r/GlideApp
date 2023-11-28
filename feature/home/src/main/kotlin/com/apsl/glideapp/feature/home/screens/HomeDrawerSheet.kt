package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Help
import com.apsl.glideapp.core.ui.icons.Logout
import com.apsl.glideapp.core.ui.icons.MyRides
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.icons.Settings
import com.apsl.glideapp.core.ui.icons.Wallet
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.home.viewmodels.UserInfo

@Immutable
data class DrawerMenuItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)

@Composable
fun HomeDrawerSheet(
    userInfo: UserInfo,
    modifier: Modifier = Modifier,
    onMyRidesClick: () -> Unit,
    onWalletClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val menuItems = remember {
        listOf(
            DrawerMenuItem(
                icon = GlideIcons.Wallet,
                title = "Wallet",
                onClick = onWalletClick
            ),
            DrawerMenuItem(
                icon = GlideIcons.MyRides,
                title = "My rides",
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
            ),
            DrawerMenuItem(
                icon = GlideIcons.Logout,
                title = "Log out",
                onClick = onLogoutClick
            )
        )
    }

    ModalDrawerSheet(modifier = modifier, drawerShape = RectangleShape) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            if (userInfo.username != null) {
                Text(
                    text = "Hi, ${userInfo.username}",
                    style = MaterialTheme.typography.headlineLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(Modifier.height(20.dp))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                StatsComponent(
                    icon = GlideIcons.Route,
                    value = userInfo.totalDistance,
                    units = "meters"
                )
                StatsComponent(
                    icon = GlideIcons.ElectricScooter,
                    value = userInfo.totalRides,
                    units = "rides"
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        menuItems.forEachIndexed { index, item ->
            if (index == 0) {
                Divider()
            }
            ListItem(
                modifier = Modifier.clickable(onClick = item.onClick),
                headlineContent = { Text(text = item.title) },
                leadingContent = {
                    Icon(imageVector = item.icon, contentDescription = null)
                }
            )
        }
    }
}

@Composable
private fun StatsComponent(icon: ImageVector, value: Int, units: String) {
    Column {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.height(4.dp))
        Text(text = value.toString(), style = MaterialTheme.typography.headlineSmall)
        Text(text = units)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDrawerSheetPreview() {
    GlideAppTheme {
        HomeDrawerSheet(
            userInfo = UserInfo(
                username = "f00b4r",
                totalDistance = 1405,
                totalRides = 23
            ),
            onMyRidesClick = {},
            onWalletClick = {},
            onLogoutClick = {}
        )
    }
}
