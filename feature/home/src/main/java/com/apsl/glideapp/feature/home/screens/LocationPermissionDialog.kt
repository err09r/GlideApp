package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.navigateToAppSettings

@Composable
fun LocationPermissionDialog(modifier: Modifier = Modifier, onNavigateBack: () -> Unit) {
    val context = LocalContext.current

    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 50.dp, vertical = 200.dp),
                text = "Location Permission Dialog"
            )
            Button(
                onClick = {
                    context.navigateToAppSettings()
                    onNavigateBack()
                }
            ) {
                Text("Go to app settings")
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPermissionDialogPreview() {
    GlideAppTheme {
        LocationPermissionDialog(onNavigateBack = {})
    }
}
