package com.apsl.glideapp.feature.auth.screens

import android.graphics.Shader
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.None
import com.apsl.glideapp.core.ui.onContentClickResettable

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable ColumnScope.() -> Unit
) {
    val backgroundColor =
        Color.Black.copy(alpha = 0.1f).compositeOver(MaterialTheme.colorScheme.primary)

    Scaffold(
        modifier = modifier.onContentClickResettable(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        contentWindowInsets = WindowInsets.None
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .onContentClickResettable()
                .padding(padding)
                .drawWithCache {
                    val largeRadialGradient = object : ShaderBrush() {
                        override fun createShader(size: Size): Shader {
                            val biggestDimension = maxOf(size.height, size.width)
                            return RadialGradientShader(
                                center = Offset(x = size.width, y = size.center.y * 0.95f),
                                radius = biggestDimension / 1.6f,
                                colors = listOf(Color.White, backgroundColor)
                            )
                        }
                    }
                    onDrawBehind {
                        drawRect(brush = largeRadialGradient)
                    }
                }
                .padding(top = 120.dp),
            shape = BottomSheetDefaults.ExpandedShape,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                content = content
            )
        }
    }
}
