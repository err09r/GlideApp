package com.apsl.glideapp.feature.auth.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val usernameTextFieldValue: String? = null,
    val passwordTextFieldValue: String? = null,
    val exception: Exception? = null
)
