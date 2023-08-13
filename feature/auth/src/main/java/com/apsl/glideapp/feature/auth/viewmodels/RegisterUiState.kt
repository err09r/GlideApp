package com.apsl.glideapp.feature.auth.viewmodels

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterUiState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val usernameTextFieldValue: String? = null,
    val passwordTextFieldValue: String? = null,
    val exception: Exception? = null
)
