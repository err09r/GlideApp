package com.apsl.glideapp.feature.auth.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.PasswordTextField
import com.apsl.glideapp.core.ui.R
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.scrollToCenterOnFocused
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.auth.common.AuthScreen
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ScreenActions(actions = viewModel.actions) { action ->
        when (action) {
            is RegisterAction.NavigateToHome -> onNavigateToHome()
            is RegisterAction.ShowError -> {
                scope.launch {
                    snackbarHostState.showSnackbar(action.error.toString())
                }
            }
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onNavigateBack,
        onRegisterClick = viewModel::register,
        onUsernameTextFieldValueChange = viewModel::updateUsernameTextFieldValue,
        onPasswordTextFieldValueChange = viewModel::updatePasswordTextFieldValue,
        onRepeatPasswordTextFieldValueChange = viewModel::updateRepeatPasswordTextFieldValue,
        onTogglePasswordVisibilityClick = viewModel::togglePasswordVisibility,
        onToggleRepeatPasswordVisibilityClick = viewModel::toggleRepeatPasswordVisibility
    )
}

@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onUsernameTextFieldValueChange: (String) -> Unit,
    onPasswordTextFieldValueChange: (String) -> Unit,
    onRepeatPasswordTextFieldValueChange: (String) -> Unit,
    onTogglePasswordVisibilityClick: () -> Unit,
    onToggleRepeatPasswordVisibilityClick: () -> Unit
) {
    when {
        uiState.isLoading -> LoadingScreen()
        else -> {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val scrollState = rememberScrollState()

            AuthScreen(snackbarHostState = snackbarHostState, scrollState = scrollState) {
                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackClick, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = GlideIcons.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Hello!", style = MaterialTheme.typography.headlineSmall)
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Create a new account and get a bonus to start",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                GlideImage(
                    imageResId = R.drawable.img_pencil,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(32.dp)
                )

                OutlinedTextField(
                    value = uiState.usernameTextFieldValue ?: "",
                    onValueChange = onUsernameTextFieldValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scrollToCenterOnFocused(scrollState),
                    label = { Text(text = "Username") },
                    isError = uiState.usernameError != null,
                    supportingText = if (uiState.usernameError != null) {
                        {
                            Text(text = uiState.usernameError)
                        }
                    } else {
                        null
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                PasswordTextField(
                    value = uiState.passwordTextFieldValue ?: "",
                    label = "Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scrollToCenterOnFocused(scrollState),
                    passwordVisible = uiState.isPasswordVisible,
                    isError = uiState.passwordError != null,
                    errorText = uiState.passwordError,
                    onTogglePasswordVisibilityClick = onTogglePasswordVisibilityClick,
                    onValueChange = onPasswordTextFieldValueChange
                )

                Spacer(Modifier.height(8.dp))

                PasswordTextField(
                    value = uiState.repeatPasswordTextFieldValue ?: "",
                    label = "Repeat password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scrollToCenterOnFocused(scrollState),
                    passwordVisible = uiState.isRepeatPasswordVisible,
                    isError = uiState.passwordError != null,
                    onTogglePasswordVisibilityClick = onToggleRepeatPasswordVisibilityClick,
                    onValueChange = onRepeatPasswordTextFieldValueChange
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        onRegisterClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.isActionButtonActive
                ) {
                    Text(
                        text = "Sign up",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    GlideAppTheme {
        RegisterScreenContent(
            uiState = RegisterUiState(),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onRegisterClick = {},
            onUsernameTextFieldValueChange = {},
            onPasswordTextFieldValueChange = {},
            onRepeatPasswordTextFieldValueChange = {},
            onTogglePasswordVisibilityClick = {},
            onToggleRepeatPasswordVisibilityClick = {}
        )
    }
}
