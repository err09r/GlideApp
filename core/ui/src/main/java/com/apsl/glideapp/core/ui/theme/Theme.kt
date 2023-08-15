package com.apsl.glideapp.core.ui.theme

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColors = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80
)

private val LightColors = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun GlideAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(systemUiController, darkTheme) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = !darkTheme
        )
    }

    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
        LocalOverscrollConfiguration provides null
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            content = content
        )
    }
}
