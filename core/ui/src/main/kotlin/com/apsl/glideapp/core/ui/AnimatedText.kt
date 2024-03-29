package com.apsl.glideapp.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.StopwatchUtils

@Composable
fun AnimatedText(
    text: String,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<Char>.() -> ContentTransform = { slideInVertically { -it } togetherWith slideOutVertically { it } },
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    AnimatedText(
        textProvider = { text },
        modifier = modifier,
        transitionSpec = transitionSpec,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
fun AnimatedText(
    textProvider: () -> String,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<Char>.() -> ContentTransform = { slideInVertically { -it } togetherWith slideOutVertically { it } },
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val previousText = remember { textProvider() }
    val newText = textProvider()
    Row(modifier = modifier) {
        for (i in newText.indices) {
            val previousChar = previousText.getOrNull(i)
            val newChar = newText[i]
            val char = if (previousChar == newChar) previousText[i] else newText[i]
            AnimatedContent(
                targetState = char,
                transitionSpec = transitionSpec,
                label = "AnimatedContent"
            ) {
                Text(
                    text = it.toString(),
                    color = color,
                    fontSize = fontSize,
                    fontStyle = fontStyle,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacing,
                    textDecoration = textDecoration,
                    textAlign = textAlign,
                    lineHeight = lineHeight,
                    overflow = overflow,
                    softWrap = softWrap,
                    maxLines = maxLines,
                    minLines = minLines,
                    onTextLayout = onTextLayout,
                    style = style
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAnimatedText() {
    GlideAppTheme {
        AnimatedText(textProvider = { "00:01" })
    }
}

@Composable
fun AnimatedStopwatchText(
    valueProvider: () -> Long,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<Char>.() -> ContentTransform = { slideInVertically { -it } togetherWith slideOutVertically { it } },
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    AnimatedText(
        textProvider = { StopwatchUtils.millisToTimeFormat(valueProvider()) },
        modifier = modifier,
        transitionSpec = transitionSpec,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
private fun AnimatedStopwatchTextPreview() {
    GlideAppTheme {
        AnimatedStopwatchText(valueProvider = { System.currentTimeMillis() })
    }
}
