@file:Suppress("Unused", "SpellCheckingInspection")

package com.apsl.glideapp.core.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickable(
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed {
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
        label = ""
    )
    this.height(spacerHeight.value)
}
