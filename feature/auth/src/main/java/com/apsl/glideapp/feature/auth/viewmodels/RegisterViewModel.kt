package com.apsl.glideapp.feature.auth.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.auth.RegisterUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun register() {
        showLoading()

        //TODO: VALIDATE USECASE
        //TODO: VALIDATE USECASE
        //TODO: VALIDATE USECASE
        //TODO: VALIDATE USECASE

        val username = uiState.value.usernameTextFieldValue
        val password = uiState.value.passwordTextFieldValue

        if (username != null && password != null) {
            viewModelScope.launch {
                registerUseCase(username = username, password = password)
                    .onSuccess {
                        _uiState.update {
                            it.copy(isRegistered = true, isLoading = false)
                        }
                    }
                    .onFailure(Timber::d)
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
    val isRegistered: Boolean = false,
    val usernameTextFieldValue: String? = null,
    val passwordTextFieldValue: String? = null,
    val exception: Exception? = null
)
