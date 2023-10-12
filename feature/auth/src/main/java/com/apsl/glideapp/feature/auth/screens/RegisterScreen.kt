package com.apsl.glideapp.feature.auth.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.auth.viewmodels.RegisterUiState
import com.apsl.glideapp.feature.auth.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onNavigateToHome()
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        onRegisterClick = viewModel::register,
        onUsernameTextFieldValueChange = viewModel::updateUsernameTextFieldValue,
        onPasswordTextFieldValueChange = viewModel::updatePasswordTextFieldValue
    )
}

@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    onRegisterClick: () -> Unit,
    onUsernameTextFieldValueChange: (String) -> Unit,
    onPasswordTextFieldValueChange: (String) -> Unit
) {
    when {
        uiState.isLoading -> LoadingScreen()
        uiState.exception != null -> Text(text = "Error occured")
        else -> {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    },
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(1f))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Register", fontSize = 32.sp)
                    }

                    Spacer(Modifier.height(32.dp))

                    OutlinedTextField(
                        value = uiState.usernameTextFieldValue ?: "",
                        onValueChange = onUsernameTextFieldValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Username") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.passwordTextFieldValue ?: "",
                        onValueChange = onPasswordTextFieldValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.None
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            shape = CircleShape,
                            onClick = {
                                keyboardController?.hide()
                                focusManager.clearFocus(true)
                                onRegisterClick()
                            }
                        ) {
                            Text(
                                text = "Register",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    GlideAppTheme {
        RegisterScreenContent(uiState = RegisterUiState(), {}, {}, {})
    }
}
