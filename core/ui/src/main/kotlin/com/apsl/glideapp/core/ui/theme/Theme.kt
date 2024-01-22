package com.apsl.glideapp.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import timber.log.Timber

private val LightColorScheme = lightColorScheme(
    primary = Colors.LightPrimary,
    onPrimary = Colors.LightOnPrimary,
    primaryContainer = Colors.LightPrimaryContainer,
    onPrimaryContainer = Colors.LightOnPrimaryContainer,
    secondary = Colors.LightSecondary,
    onSecondary = Colors.LightOnSecondary,
    secondaryContainer = Colors.LightSecondaryContainer,
    onSecondaryContainer = Colors.LightOnSecondaryContainer,
    tertiary = Colors.LightTertiary,
    onTertiary = Colors.LightOnTertiary,
    tertiaryContainer = Colors.LightTertiaryContainer,
    onTertiaryContainer = Colors.LightOnTertiaryContainer,
    error = Colors.LightError,
    errorContainer = Colors.LightErrorContainer,
    onError = Colors.LightOnError,
    onErrorContainer = Colors.LightOnErrorContainer,
    background = Colors.LightBackground,
    onBackground = Colors.LightOnBackground,
    surface = Colors.LightSurface,
    onSurface = Colors.LightOnSurface,
    surfaceVariant = Colors.LightSurfaceVariant,
    onSurfaceVariant = Colors.LightOnSurfaceVariant,
    outline = Colors.LightOutline,
    inverseOnSurface = Colors.LightInverseOnSurface,
    inverseSurface = Colors.LightInverseSurface,
    inversePrimary = Colors.LightInversePrimary,
    surfaceTint = Colors.LightSurfaceTint,
    outlineVariant = Colors.LightOutlineVariant,
    scrim = Colors.LightScrim
)

private val DarkColorScheme = darkColorScheme(
    primary = Colors.DarkPrimary,
    onPrimary = Colors.DarkOnPrimary,
    primaryContainer = Colors.DarkPrimaryContainer,
    onPrimaryContainer = Colors.DarkOnPrimaryContainer,
    secondary = Colors.DarkSecondary,
    onSecondary = Colors.DarkOnSecondary,
    secondaryContainer = Colors.DarkSecondaryContainer,
    onSecondaryContainer = Colors.DarkOnSecondaryContainer,
    tertiary = Colors.DarkTertiary,
    onTertiary = Colors.DarkOnTertiary,
    tertiaryContainer = Colors.DarkTertiaryContainer,
    onTertiaryContainer = Colors.DarkOnTertiaryContainer,
    error = Colors.DarkError,
    errorContainer = Colors.DarkErrorContainer,
    onError = Colors.DarkOnError,
    onErrorContainer = Colors.DarkOnErrorContainer,
    background = Colors.DarkBackground,
    onBackground = Colors.DarkOnBackground,
    surface = Colors.DarkSurface,
    onSurface = Colors.DarkOnSurface,
    surfaceVariant = Colors.DarkSurfaceVariant,
    onSurfaceVariant = Colors.DarkOnSurfaceVariant,
    outline = Colors.DarkOutline,
    inverseOnSurface = Colors.DarkInverseOnSurface,
    inverseSurface = Colors.DarkInverseSurface,
    inversePrimary = Colors.DarkInversePrimary,
    surfaceTint = Colors.DarkSurfaceTint,
    outlineVariant = Colors.DarkOutlineVariant,
    scrim = Colors.DarkScrim
)

@Composable
fun GlideAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SideEffect {
        Timber.d("Current color scheme: $colorScheme; is dynamic: $dynamicColor")
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.run {
                statusBarColor = Color.Transparent.toArgb()
                navigationBarColor = Color.Transparent.toArgb()
            }
            WindowCompat.getInsetsController(window, view).run {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
        LocalOverscrollConfiguration provides null
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
