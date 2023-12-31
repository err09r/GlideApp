package com.apsl.glideapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.components.ScreenTopBar
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun FeatureScreen(
    topBarText: String,
    onBackClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            ScreenTopBar(text = topBarText, onBackClick = onBackClick)
        },
        backgroundColor = Color.LightGray.copy(alpha = 0.2f)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureScreenPreview() {
    GlideAppTheme {
        FeatureScreen(topBarText = "My Wallet", onBackClick = {}) {
        }
    }
}
