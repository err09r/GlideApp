package com.apsl.glideapp.feature.auth.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.auth.RegisterFieldsVerificationResult
import com.apsl.glideapp.core.domain.auth.RegisterUseCase
import com.apsl.glideapp.core.domain.auth.VerifyRegisterFieldsUseCase
import com.apsl.glideapp.core.domain.auth.isError
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val verifyRegisterFieldsUseCase: VerifyRegisterFieldsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<RegisterAction>()
    val actions = _actions.receiveAsFlow()

    fun register() {
        showLoading()

        val username = uiState.value.usernameTextFieldValue
        val password = uiState.value.passwordTextFieldValue
        val repeatPassword = uiState.value.repeatPasswordTextFieldValue

        if (username != null && password != null && repeatPassword != null) {

            val result = verifyRegisterFieldsUseCase(
                username = username,
                password = password,
                repeatPassword = repeatPassword
            )

            if (result.isError) {
                resetPasswordFields()
            }

            when (result) {
                RegisterFieldsVerificationResult.InvalidUsernameFormat -> {
                    _uiState.update {
                        it.copy(
                            usernameError = "Invalid username format",
                            isLoading = false
                        )
                    }
                    return
                }

                RegisterFieldsVerificationResult.InvalidPasswordFormat -> {
                    _uiState.update {
                        it.copy(
                            passwordError = "Invalid password format",
                            isLoading = false
                        )
                    }
                    return
                }

                RegisterFieldsVerificationResult.PasswordsDoNotMatch -> {
                    _uiState.update {
                        it.copy(
                            passwordError = "Passwords do not match",
                            isLoading = false
                        )
                    }
                    return
                }

                else -> Unit
            }

            viewModelScope.launch {
                registerUseCase(username = username, password = password)
                    .onSuccess {
                        _uiState.update {
                            it.copy(isLoading = false, usernameError = null)
                        }
                        resetPasswordFields()
                        _actions.send(RegisterAction.NavigateToHome)
                    }
                    .onFailure { throwable ->
                        Timber.d(throwable.message)
                        _uiState.update { it.copy(isLoading = false) }
                        resetPasswordFields()
                        _actions.send(RegisterAction.ShowError(throwable.message))
                    }
            }
        } else {
            hideLoading()
        }
    }

    private fun resetPasswordFields() {
        _uiState.update {
            it.copy(
                passwordTextFieldValue = null,
                repeatPasswordTextFieldValue = null,
                isPasswordVisible = false,
                isRepeatPasswordVisible = false,
                passwordError = null
            )
        }
    }

    fun updateUsernameTextFieldValue(input: String?) {
        _uiState.update {
            it.copy(usernameTextFieldValue = input)
        }
    }

    fun updatePasswordTextFieldValue(input: String?) {
        _uiState.update {
            it.copy(passwordTextFieldValue = input)
        }
    }

    fun updateRepeatPasswordTextFieldValue(input: String?) {
        _uiState.update {
            it.copy(repeatPasswordTextFieldValue = input)
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    fun toggleRepeatPasswordVisibility() {
        _uiState.update {
            it.copy(isRepeatPasswordVisible = !it.isRepeatPasswordVisible)
        }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        _uiState.update { it.copy(isLoading = false) }
    }
}

@Immutable
data class RegisterUiState(
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false,
    val usernameTextFieldValue: String? = null,
    val passwordTextFieldValue: String? = null,
    val repeatPasswordTextFieldValue: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null
) {
    val isActionButtonActive: Boolean
        get() = !usernameTextFieldValue.isNullOrBlank()
                && !passwordTextFieldValue.isNullOrBlank()
                && !repeatPasswordTextFieldValue.isNullOrBlank()
}

@Immutable
sealed interface RegisterAction {
    data class ShowError(val error: String?) : RegisterAction
    data object NavigateToHome : RegisterAction
}

