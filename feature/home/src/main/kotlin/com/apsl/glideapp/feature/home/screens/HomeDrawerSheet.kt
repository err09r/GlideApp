package com.apsl.glideapp.feature.home.screens

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Globe
import com.apsl.glideapp.core.ui.icons.Help
import com.apsl.glideapp.core.ui.icons.Logout
import com.apsl.glideapp.core.ui.icons.MyRides
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.icons.Settings
import com.apsl.glideapp.core.ui.icons.Wallet
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.openAppLanguageSettings
import com.apsl.glideapp.core.util.android.openAppSettings
import com.apsl.glideapp.feature.home.viewmodels.UserInfo
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class DrawerMenuItem(
    val icon: ImageVector,
    @StringRes val titleResId: Int,
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
    val context = LocalContext.current
    val menuItems = remember {
        listOf(
            DrawerMenuItem(
                icon = GlideIcons.Wallet,
                titleResId = CoreR.string.home_drawer_menu_wallet,
                onClick = onWalletClick
            ),
            DrawerMenuItem(
                icon = GlideIcons.MyRides,
                titleResId = CoreR.string.home_drawer_menu_rides,
                onClick = onMyRidesClick
            ),
            DrawerMenuItem(
                icon = GlideIcons.Help,
                titleResId = CoreR.string.home_drawer_menu_help,
                onClick = {}
            ),
            DrawerMenuItem(
                icon = GlideIcons.Settings,
                titleResId = CoreR.string.home_drawer_menu_settings,
                onClick = context::openAppSettings //TODO: Navigate to SettingsScreen instead of app settings
            ),
            DrawerMenuItem(
                icon = GlideIcons.Logout,
                titleResId = CoreR.string.home_drawer_menu_logout,
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
                    text = stringResource(CoreR.string.home_drawer_header, userInfo.username),
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
                    units = stringResource(CoreR.string.meters) //TODO: Handle `Quantity strings (plurals)`
                )
                StatsComponent(
                    icon = GlideIcons.ElectricScooter,
                    value = userInfo.totalRides,
                    units = stringResource(CoreR.string.rides) //TODO: Handle `Quantity strings (plurals)`
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
                headlineContent = { Text(text = stringResource(item.titleResId)) },
                leadingContent = {
                    Icon(imageVector = item.icon, contentDescription = null)
                }
            )
        }

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                IconButton(
                    onClick = context::openAppLanguageSettings,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = GlideIcons.Globe,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
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
