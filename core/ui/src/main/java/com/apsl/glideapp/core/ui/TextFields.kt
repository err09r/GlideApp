package com.apsl.glideapp.core.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.icons.Eye
import com.apsl.glideapp.core.ui.icons.EyeClosed
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun PasswordTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    passwordVisible: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
    onTogglePasswordVisibilityClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val image = remember(passwordVisible) {
        mutableStateOf(if (passwordVisible) GlideIcons.EyeClosed else GlideIcons.Eye)
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = onTogglePasswordVisibilityClick) {
                Icon(imageVector = image.value, contentDescription = null)
            }
        },
        supportingText = if (errorText != null) {
            { Text(text = errorText) }
        } else {
            null
        },
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    GlideAppTheme {
        PasswordTextField(
            value = "qwerty",
            label = "Password",
            passwordVisible = true,
            onTogglePasswordVisibilityClick = {},
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldNotVisiblePreview() {
    GlideAppTheme {
        PasswordTextField(
            value = "qwerty",
            label = "Password",
            passwordVisible = false,
            onTogglePasswordVisibilityClick = {},
            onValueChange = {}
        )
    }
}
