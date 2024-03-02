package com.apsl.glideapp.core.datastore

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    val authToken: Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun deleteAuthToken()
    val currentUser: Flow<CurrentUser?>
    suspend fun saveCurrentUser(user: CurrentUser): CurrentUser?
    val walletVisited: Flow<Boolean?>
    suspend fun saveWalletVisited(value: Boolean): Boolean?
    val lastMapCameraPosition: Flow<LastMapCameraPosition?>
    suspend fun saveLastMapCameraPosition(position: LastMapCameraPosition): LastMapCameraPosition?
    val isRideModeActive: Flow<Boolean?>
    suspend fun saveRideModeActive(value: Boolean): Boolean?
    val unlockDistance: Flow<Double?>
    suspend fun saveUnlockDistance(distance: Double): Double?
}
