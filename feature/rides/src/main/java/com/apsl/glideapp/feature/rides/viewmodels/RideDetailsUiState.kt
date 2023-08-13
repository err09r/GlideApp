package com.apsl.glideapp.feature.rides.viewmodels

import androidx.compose.runtime.Immutable
import com.apsl.glideapp.feature.rides.models.RideDetailsUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

@Immutable
data class RideDetailsUiState(
    val isLoading: Boolean = false,
    val ride: RideDetailsUiModel? = null,
    val mapCameraBounds: LatLngBounds = LatLngBounds(LatLng(0.0, 0.0), LatLng(0.0, 0.0)),
    val error: RideDetailsUiError? = null
)
