package com.apsl.glideapp.core.datastore

import androidx.datastore.core.DataStore
import com.apsl.glideapp.common.models.Coordinates
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<AppPreferences>,
    private val encryptedDataStore: DataStore<EncryptedAppPreferences>
) : AppDataStore {

    override val authToken: Flow<String?> = encryptedDataStore.data.map { it.authToken }

    override suspend fun saveAuthToken(token: String) {
        encryptedDataStore.updateData { it.copy(authToken = token) }
    }

    override suspend fun deleteAuthToken() {
        encryptedDataStore.updateData { it.copy(authToken = null) }
    }

    override val lastUserLocation: Flow<Coordinates?> = dataStore.data.map { it.lastUserLocation }

    override suspend fun saveLastUserLocation(location: Coordinates): Coordinates? {
        val updated = dataStore.updateData { it.copy(lastUserLocation = location) }
        return updated.lastUserLocation
    }

    override val isRideModeActive: Flow<Boolean?> = dataStore.data.map { it.isRideModeActive }

    override suspend fun saveRideModeActive(value: Boolean): Boolean? {
        val updated = dataStore.updateData { it.copy(isRideModeActive = value) }
        return updated.isRideModeActive
    }

    override val unlockDistance: Flow<Double?> = dataStore.data.map { it.unlockDistance }

    override suspend fun saveUnlockDistance(distance: Double): Double? {
        val updated = dataStore.updateData { it.copy(unlockDistance = distance) }
        return updated.unlockDistance
    }
}
