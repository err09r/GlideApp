package com.apsl.glideapp.feature.rides.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.apsl.glideapp.core.domain.ride.GetRideByIdUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.util.maps.toLatLngBounds
import com.apsl.glideapp.feature.rides.models.RideDetailsUiModel
import com.apsl.glideapp.feature.rides.models.toRideDetailsUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

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
    val mapCameraBounds: LatLngBounds = LatLngBounds(LatLng(0.0, 0.0), LatLng(0.0, 0.0)),
    val error: RideDetailsUiError? = null
)
