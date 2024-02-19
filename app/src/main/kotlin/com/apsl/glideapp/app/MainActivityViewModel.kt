package com.apsl.glideapp.app

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.config.AppConfigRepository
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

@Immutable
data class MainActivityUiState(
    val isSyncing: Boolean = false
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    private val zoneRepository: ZoneRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState = _uiState.asStateFlow()

    fun sync() {
        viewModelScope.launch {
            try {
                withTimeout(SPLASH_SCREEN_DURATION_MS) {
                    _uiState.update { it.copy(isSyncing = true) }
                    updateAppConfiguration()
                }
            } catch (e: TimeoutCancellationException) {
                Timber.d("Splash screen timeout")
            } finally {
                _uiState.update { it.copy(isSyncing = false) }
            }
        }
    }

    private suspend fun updateAppConfiguration() {
        try {
            appConfigRepository.updateAppConfig()
            zoneRepository.updateAllZones()
        } catch (e: Exception) {
            if (e !is CancellationException) {
                Timber.d("Failed to fetch app configuration: ${e.message}")
            }
        }
    }

    private companion object {
        private const val SPLASH_SCREEN_DURATION_MS = 1000L
    }
}
