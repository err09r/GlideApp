package com.apsl.glideapp.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.R

private val nuritoFamily = FontFamily(
    Font(R.font.nurito_light, weight = FontWeight.Light),
    Font(R.font.nurito_regular, weight = FontWeight.Normal),
    Font(R.font.nurito_medium, weight = FontWeight.Medium),
    Font(R.font.nurito_semibold, weight = FontWeight.SemiBold),
    Font(R.font.nurito_bold, weight = FontWeight.Bold)
)

private val platformStyle = PlatformTextStyle(includeFontPadding = false)

internal val Typography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        fontFamily = nuritoFamily,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        platformStyle = platformStyle
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        fontFamily = nuritoFamily,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        fontFamily = nuritoFamily,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        fontFamily = nuritoFamily,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        fontFamily = nuritoFamily,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        fontFamily = nuritoFamily,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        fontFamily = nuritoFamily,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = nuritoFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        platformStyle = platformStyle
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        fontFamily = nuritoFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        platformStyle = platformStyle
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = nuritoFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        platformStyle = platformStyle
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        fontFamily = nuritoFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        platformStyle = platformStyle
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = nuritoFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        platformStyle = platformStyle
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        fontFamily = nuritoFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        platformStyle = platformStyle
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        fontFamily = nuritoFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        platformStyle = platformStyle
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        fontFamily = nuritoFamily,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = platformStyle
    )
)
