package com.apsl.glideapp.feature.rides.viewmodels

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import com.apsl.glideapp.feature.rides.models.RideUiModel

@Immutable
data class AllRidesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val rides: LazyPagingItems<RideUiModel>? = null,
    val error: AllRidesUiError? = null
)
