package com.apsl.glideapp.feature.auth.screens

import android.graphics.Shader
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.components.GlideImage
import com.apsl.glideapp.core.ui.components.PasswordTextField
import com.apsl.glideapp.core.ui.onContentClickResettable
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.auth.viewmodels.LoginUiState
import com.apsl.glideapp.feature.auth.viewmodels.LoginViewModel
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onNavigateToHome()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onLoginClick = viewModel::logIn,
        onSignUpClick = onNavigateToRegister,
        onUsernameTextFieldValueChange = viewModel::updateUsernameTextFieldValue,
        onPasswordTextFieldValueChange = viewModel::updatePasswordTextFieldValue,
        onTogglePasswordVisibilityClick = viewModel::togglePasswordVisibility
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onUsernameTextFieldValueChange: (String) -> Unit,
    onPasswordTextFieldValueChange: (String) -> Unit,
    onTogglePasswordVisibilityClick: () -> Unit
) {
    when {
        uiState.isLoading -> LoadingScreen()
        else -> {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val snackbarHostState = remember { SnackbarHostState() }
            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.Expanded,
                    skipHiddenState = true
                ),
                snackbarHostState = snackbarHostState
            )

            val backgroundColor =
                Color.Black.copy(alpha = 0.1f).compositeOver(MaterialTheme.colorScheme.primary)

            LaunchedEffect(uiState.error) {
                if (uiState.error != null) {
                    snackbarHostState.showSnackbar(uiState.error)
                }
            }

            BottomSheetScaffold(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .onContentClickResettable()
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.height(16.dp))
                        Text(text = "Welcome!", style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Sign in to start a ride",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(28.dp)
                        ) {
                            GlideImage(CoreR.drawable.img_email)
                        }

                        OutlinedTextField(
                            value = uiState.usernameTextFieldValue ?: "",
                            onValueChange = onUsernameTextFieldValueChange,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Username") },
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
                            modifier = Modifier.fillMaxWidth(),
                            passwordVisible = uiState.isPasswordVisible,
                            onTogglePasswordVisibilityClick = onTogglePasswordVisibilityClick,
                            onValueChange = onPasswordTextFieldValueChange,
                        )

                        Spacer(Modifier.height(32.dp))

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
                                text = "Sign In",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        Spacer(Modifier.height(96.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Don't have an account?")
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Sign up",
                                modifier = Modifier.clickable(onClick = onSignUpClick),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                    }
                },
                modifier = Modifier.onContentClickResettable(),
                scaffoldState = scaffoldState,
                sheetShadowElevation = 8.dp,
                sheetDragHandle = null,
                sheetSwipeEnabled = false,
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            ) { padding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val largeRadialGradient = object : ShaderBrush() {
                            override fun createShader(size: Size): Shader {
                                val biggestDimension = maxOf(size.height, size.width)
                                return RadialGradientShader(
                                    center = Offset(x = size.width, y = size.center.y * 0.95f),
                                    radius = biggestDimension / 1.6f,
                                    colors = listOf(Color.White, backgroundColor)
                                )
                            }
                        }
                        onDrawBehind {
                            drawRect(brush = largeRadialGradient)
                        }
                    }
                    .padding(padding)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    GlideAppTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            onLoginClick = {},
            onSignUpClick = {},
            onUsernameTextFieldValueChange = {},
            onPasswordTextFieldValueChange = {},
            onTogglePasswordVisibilityClick = {}
        )
    }
}
