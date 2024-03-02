@file:Suppress("Unused", "SpellCheckingInspection")

package com.apsl.glideapp.core.ui

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.clickable(
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = onClick,
        role = role,
        indication = indication,
        interactionSource = remember { MutableInteractionSource() }
    )
}

fun Modifier.onContentClickResettable(
    hideKeyboard: Boolean = true,
    clearFocus: Boolean = true
): Modifier = composed {
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val focusManager: FocusManager = LocalFocusManager.current
    this.clickable(indication = null) {
        if (hideKeyboard) {
            keyboardController?.hide()
        }
        if (clearFocus) {
            focusManager.clearFocus(true)
        }
    }
}

fun Modifier.imeCollapsible(initialHeight: Dp): Modifier = composed {
    val imeHeight = WindowInsets.imeAnimationTarget.getBottom(LocalDensity.current).toDp()
    val spacerHeight = animateDpAsState(
        targetValue = if (imeHeight == 0.dp) initialHeight else 0.dp,
        label = "SpacerHeight"
    )
    this.height(spacerHeight.value)
}

fun Modifier.paddingBeforeSeparator(apply: Boolean): Modifier {
    return this.run {
        if (apply) {
            this.padding(bottom = 24.dp)
        } else {
            this
        }
    }
}

fun Modifier.scrollToCenterOnFocused(scrollState: ScrollState): Modifier = composed {
    val scope = rememberCoroutineScope()
    val centerY = remember { mutableFloatStateOf(0f) }
    this
        .onGloballyPositioned {
            centerY.floatValue = it.positionInParent().y - it.size.center.y
        }
        .onFocusChanged {
            if (it.isFocused && !scrollState.isScrollInProgress) {
                scope.launch {
                    delay(200)
                    scrollState.animateScrollTo(
                        value = centerY.floatValue.roundToInt(),
                        animationSpec = TweenSpec()
                    )
                }
            }
        }
}
