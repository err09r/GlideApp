package com.apsl.glideapp.feature.auth.login

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.auth.LoginUseCase
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
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class LoginUiState(
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isRememberMeChecked: Boolean = true,
    val usernameTextFieldValue: String? = null,
    val passwordTextFieldValue: String? = null,
    val error: String? = null
) {
    val isActionButtonActive: Boolean
        get() = !usernameTextFieldValue.isNullOrBlank() && !passwordTextFieldValue.isNullOrBlank()
}

@Immutable
sealed interface LoginAction {
    data class ShowError(@StringRes val errorResId: Int) : LoginAction
    data object NavigateToRegister : LoginAction
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<LoginAction>()
    val actions = _actions.receiveAsFlow()

    fun logIn() {
        showLoading()

        val username = uiState.value.usernameTextFieldValue
        val password = uiState.value.passwordTextFieldValue

        if (username != null && password != null) {
            viewModelScope.launch {
                loginUseCase(username = username, password = password)
                    .onSuccess {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                    .onFailure { throwable ->
                        Timber.d(throwable.message)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                passwordTextFieldValue = null,
                                isPasswordVisible = false
                            )
                        }
                        _actions.send(LoginAction.ShowError(errorResId = CoreR.string.error_connection))
                    }
            }
        } else {
            hideLoading()
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

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleRememberMe() {
        _uiState.update { it.copy(isRememberMeChecked = !it.isRememberMeChecked) }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        _uiState.update { it.copy(isLoading = false) }
    }
}
