package com.apsl.glideapp.core.datastore

import androidx.datastore.core.DataStore
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

    override val currentUser: Flow<CurrentUser?> = dataStore.data.map { it.currentUser }

    override suspend fun saveCurrentUser(user: CurrentUser): CurrentUser? {
        val updated = dataStore.updateData { it.copy(currentUser = user) }
        return updated.currentUser
    }

    override val walletVisited: Flow<Boolean?> = dataStore.data.map { it.walletVisited }

    override suspend fun saveWalletVisited(value: Boolean): Boolean? {
        val updated = dataStore.updateData { it.copy(walletVisited = value) }
        return updated.walletVisited
    }

    override val lastMapCameraPosition: Flow<LastMapCameraPosition?> =
        dataStore.data.map { it.lastMapCameraPosition }

    override suspend fun saveLastMapCameraPosition(position: LastMapCameraPosition): LastMapCameraPosition? {
        val updated = dataStore.updateData { it.copy(lastMapCameraPosition = position) }
        return updated.lastMapCameraPosition
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
