package com.apsl.glideapp.app

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.config.AppConfigRepository
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    private val zoneRepository: ZoneRepository
) : BaseViewModel() {

    fun updateAppConfiguration() {
        viewModelScope.launch {
            try {
                appConfigRepository.updateAppConfig()
                zoneRepository.updateAllZones()
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }
}
