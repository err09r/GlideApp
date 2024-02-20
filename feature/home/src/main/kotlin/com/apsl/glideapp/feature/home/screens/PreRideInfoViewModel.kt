package com.apsl.glideapp.feature.home.screens

import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.ride.SendPreRideInfoEventUseCase
import com.apsl.glideapp.core.model.PreRideInfoEvent
import com.apsl.glideapp.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PreRideInfoViewModel @Inject constructor(
    private val sendPreRideInfoEventUseCase: SendPreRideInfoEventUseCase
) : BaseViewModel() {

    fun accept() {
        viewModelScope.launch {
            sendPreRideInfoEventUseCase(event = PreRideInfoEvent.Accepted)
        }
    }

    fun reject() {
        viewModelScope.launch {
            sendPreRideInfoEventUseCase(event = PreRideInfoEvent.Rejected)
        }
    }
}
