package com.apsl.glideapp.feature.rides.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.ride.GetRideByIdUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.util.maps.MapsConfiguration
import com.apsl.glideapp.core.util.maps.toLatLngBounds
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RideDetailsViewModel @Inject constructor(
    private val getRideByIdUseCase: GetRideByIdUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RideDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun getRideById(id: String) {
        viewModelScope.launch {
            showLoading()
            getRideByIdUseCase(id)
                .onSuccess { ride ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            ride = ride.toRideDetailsUiModel(),
                            mapCameraBounds = ride.route.points.toLatLngBounds()
                        )
                    }
                }
                .onFailure { Timber.d(it.message) }
        }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }
}

@Immutable
data class RideDetailsUiState(
    val isLoading: Boolean = false,
    val ride: RideDetailsUiModel? = null,
    val mapCameraBounds: LatLngBounds = MapsConfiguration.initialRideDetailsCameraBounds,
    val error: RideDetailsUiError? = null
)
