package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.location.LocationClient
import com.apsl.glideapp.core.domain.location.LocationRepository
import com.apsl.glideapp.core.domain.location.UserLocation
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocationRepositoryImpl @Inject constructor(
    private val appDataStore: AppDataStore,
    locationClient: LocationClient
) : LocationRepository {

    override val userLocation: Flow<Result<UserLocation>> = locationClient.userLocation

    override suspend fun saveLastUserLocation(location: Coordinates) {
        appDataStore.saveLastUserLocation(Json.encodeToString(location))
    }

    override suspend fun getLastSavedUserLocation(): Coordinates? {
        return appDataStore.getLastSavedUserLocation().firstOrNull()?.let {
            Json.decodeFromString(it)
        }
    }
}
