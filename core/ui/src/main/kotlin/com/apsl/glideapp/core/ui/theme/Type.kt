@file:Suppress("Unused", "SpellCheckingInspection")

package com.apsl.glideapp.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.R

private val nunitoFamily = FontFamily(
    Font(
        resId = R.font.nunito_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_regular_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_semibold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_semibold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

private val nunitoSansFamily = FontFamily(
    Font(
        resId = R.font.nunito_sans_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_sans_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_regular_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_sans_semibold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_semibold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_sans_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_sans_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

private val platformStyle = PlatformTextStyle(includeFontPadding = false)

internal val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoFamily,
        letterSpacing = (-0.25).sp,
        lineHeight = 64.sp,
        platformStyle = platformStyle
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 52.sp,
        platformStyle = platformStyle
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        platformStyle = platformStyle
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        platformStyle = platformStyle
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 36.sp,
        platformStyle = platformStyle
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        platformStyle = platformStyle
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = nunitoFamily,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
        platformStyle = platformStyle
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = nunitoFamily,
        letterSpacing = 0.1.sp,
        lineHeight = 24.sp,
        platformStyle = platformStyle
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = nunitoFamily,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
        platformStyle = platformStyle
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp,
        platformStyle = platformStyle
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
        platformStyle = platformStyle
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp,
        platformStyle = platformStyle
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
        platformStyle = platformStyle
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 16.sp,
        platformStyle = platformStyle
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = nunitoSansFamily,
        letterSpacing = 0.5.sp,
        lineHeight = 16.sp,
        platformStyle = platformStyle
    )
)
