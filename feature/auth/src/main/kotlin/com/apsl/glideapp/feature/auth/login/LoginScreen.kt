package com.apsl.glideapp.feature.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.PasswordTextField
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.ui.clickable
import com.apsl.glideapp.core.ui.scrollToCenterOnFocused
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.auth.common.AuthScreen
import kotlinx.coroutines.launch
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    ScreenActions(viewModel.actions) { action ->
        when (action) {
            is LoginAction.NavigateToHome -> onNavigateToHome()
            is LoginAction.NavigateToRegister -> onNavigateToRegister()
            is LoginAction.ShowError -> {
                scope.launch {
                    val error = context.getString(action.errorResId)
                    snackbarHostState.showSnackbar(error)
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onLoginClick = viewModel::logIn,
        onSignUpClick = onNavigateToRegister,
        onUsernameTextFieldValueChange = viewModel::updateUsernameTextFieldValue,
        onPasswordTextFieldValueChange = viewModel::updatePasswordTextFieldValue,
        onTogglePasswordVisibilityClick = viewModel::togglePasswordVisibility,
        onToggleRememberMe = viewModel::toggleRememberMe
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    snackbarHostState: SnackbarHostState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onUsernameTextFieldValueChange: (String) -> Unit,
    onPasswordTextFieldValueChange: (String) -> Unit,
    onTogglePasswordVisibilityClick: () -> Unit,
    onToggleRememberMe: () -> Unit
) {
    when {
        uiState.isLoading -> LoadingScreen()
        else -> {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val scrollState = rememberScrollState()

            AuthScreen(snackbarHostState = snackbarHostState, scrollState = scrollState) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(CoreR.string.login_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(CoreR.string.login_description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )

                GlideImage(
                    imageResId = CoreR.drawable.img_email,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(32.dp)
                )

                OutlinedTextField(
                    value = uiState.usernameTextFieldValue ?: "",
                    onValueChange = onUsernameTextFieldValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scrollToCenterOnFocused(scrollState),
                    label = { Text(text = stringResource(CoreR.string.username)) },
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
                    label = stringResource(CoreR.string.password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .scrollToCenterOnFocused(scrollState),
                    passwordVisible = uiState.isPasswordVisible,
                    onTogglePasswordVisibilityClick = onTogglePasswordVisibilityClick,
                    onValueChange = onPasswordTextFieldValueChange,
                )

                RememberMeCheckbox(
                    checked = uiState.isRememberMeChecked,
                    onToggleRememberMe = onToggleRememberMe,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        onLoginClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.isActionButtonActive
                ) {
                    Text(
                        text = stringResource(CoreR.string.login_button),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(Modifier.height(96.dp))
                Spacer(Modifier.weight(1f))

                DontHaveAccountText(
                    onSignUpClick = onSignUpClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun RememberMeCheckbox(
    checked: Boolean,
    onToggleRememberMe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = { onToggleRememberMe() })
        Text(
            text = stringResource(CoreR.string.remember_me),
            modifier = Modifier.clickable(indication = null, onClick = onToggleRememberMe)
        )
    }
}

@Composable
private fun DontHaveAccountText(onSignUpClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(CoreR.string.dont_have_an_account),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(CoreR.string.sign_up),
            modifier = Modifier.clickable(onClick = onSignUpClick),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    GlideAppTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            snackbarHostState = SnackbarHostState(),
            onLoginClick = {},
            onSignUpClick = {},
            onUsernameTextFieldValueChange = {},
            onPasswordTextFieldValueChange = {},
            onTogglePasswordVisibilityClick = {},
            onToggleRememberMe = {}
        )
    }
}
