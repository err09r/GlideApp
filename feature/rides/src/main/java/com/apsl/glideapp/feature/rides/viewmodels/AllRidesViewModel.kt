package com.apsl.glideapp.feature.rides.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.Route
import com.apsl.glideapp.core.domain.ride.GetAllRideCoordinatesUseCase
import com.apsl.glideapp.core.domain.ride.GetUserRidesPaginatedUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.feature.rides.models.RideUiModel
import com.apsl.glideapp.feature.rides.models.toRideUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class AllRidesViewModel @Inject constructor(
    getUserRidesPaginatedUseCase: GetUserRidesPaginatedUseCase,
    getAllRideCoordinatesUseCase: GetAllRideCoordinatesUseCase // Used ONLY for Paging3 workaround
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AllRidesUiState())
    val uiState = _uiState.asStateFlow()

    private val rideCoordinates = getAllRideCoordinatesUseCase()
        .distinctUntilChanged()
        .shareIn(scope = viewModelScope, started = SharingStarted.Eagerly, replay = 1)

    private suspend fun getRideCoordinatesByRideId(rideId: String): List<Coordinates> {
        return rideCoordinates
            .firstOrNull()
            ?.filter { it.rideId == rideId }
            ?.map { Coordinates(it.latitude, it.longitude) } ?: emptyList()
    }

    val pagingData = getUserRidesPaginatedUseCase()
        .map { pagingData ->
            pagingData.map { ride ->
                ride
                    .copy(route = Route(points = getRideCoordinatesByRideId(ride.id)))
                    .toRideUiModel()
            }
        }
        .cachedIn(viewModelScope)

    fun onNewPagerLoadState(pagingItems: LazyPagingItems<RideUiModel>) {
        val loadState = pagingItems.loadState.refresh
        when {
            loadState is LoadState.Loading -> {
                _uiState.update { it.copy(isLoading = true, isRefreshing = true) }
            }

            loadState is LoadState.Error && pagingItems.itemCount == 0 -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = AllRidesUiError(loadState.error)
                    )
                }
            }

            pagingItems.itemCount == 0 -> _uiState.update {
                it.copy(isLoading = false, isRefreshing = false)
            }

            else -> {
                _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false, rides = pagingItems)
                }
            }
        }
    }

    fun refresh() {
        uiState.value.rides?.refresh()
    }
}
