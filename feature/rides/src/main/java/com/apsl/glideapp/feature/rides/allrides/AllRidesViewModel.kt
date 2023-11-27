package com.apsl.glideapp.feature.rides.allrides

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.models.Route
import com.apsl.glideapp.common.util.format
import com.apsl.glideapp.core.domain.ride.GetAllRideCoordinatesUseCase
import com.apsl.glideapp.core.domain.ride.GetUserRidesPaginatedUseCase
import com.apsl.glideapp.core.domain.user.GetUserUseCase
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.ui.ComposePagingItems
import com.apsl.glideapp.core.ui.toComposePagingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AllRidesViewModel @Inject constructor(
    getUserRidesPaginatedUseCase: GetUserRidesPaginatedUseCase,
    getAllRideCoordinatesUseCase: GetAllRideCoordinatesUseCase, // Used ONLY for Paging3 workaround
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AllRidesUiState())
    val uiState = _uiState.asStateFlow()

    private val rideCoordinates = getAllRideCoordinatesUseCase()
        .distinctUntilChanged()
        .shareIn(scope = viewModelScope, started = SharingStarted.Eagerly, replay = 1)

    fun refreshRidesSummary() {
        viewModelScope.launch {
            getUserUseCase().onSuccess { user ->
                if (user == null) {
                    return@onSuccess
                }
                _uiState.update {
                    it.copy(
                        totalRides = user.totalRides.toString(),
                        totalDistance = (user.totalDistance / 1000).format(1)
                    )
                }
            }
        }
    }

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
        Timber.d("Load state: $loadState")
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
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        rides = pagingItems.toComposePagingItems()
                    )
                }
            }
        }
    }

    fun refresh() {
        uiState.value.rides?.refresh()
    }
}

@Immutable
data class AllRidesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val rides: ComposePagingItems<RideUiModel>? = null,
    val totalRides: String = "0",
    val totalDistance: String = "0,0",
    val error: AllRidesUiError? = null
)
