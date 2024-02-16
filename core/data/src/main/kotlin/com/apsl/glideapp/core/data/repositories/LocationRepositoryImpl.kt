package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.datastore.LastMapCameraPosition
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.domain.location.LocationRepository
import com.apsl.glideapp.core.model.MapCameraPosition
import com.apsl.glideapp.core.model.UserLocation
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LocationRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStore,
    locationClient: LocationClient
) : LocationRepository {

    override val userLocation: Flow<Result<UserLocation>> = locationClient.userLocation

    override suspend fun saveLastMapCameraPosition(position: MapCameraPosition) {
        appDataStore.saveLastMapCameraPosition(position.toLastMapCameraPosition())
    }

    override suspend fun getLastMapCameraPosition(): MapCameraPosition? {
        return appDataStore.lastMapCameraPosition.firstOrNull()?.toMapCameraPosition()
    }

    private fun LastMapCameraPosition.toMapCameraPosition(): MapCameraPosition {
        return MapCameraPosition(latitude = latitude, longitude = longitude, zoom = zoom)
    }

    private fun MapCameraPosition.toLastMapCameraPosition(): LastMapCameraPosition {
        return LastMapCameraPosition(latitude = latitude, longitude = longitude, zoom = zoom)
    }
}
