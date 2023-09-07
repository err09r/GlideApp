package com.apsl.glideapp.core.datastore

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    suspend fun saveAuthToken(token: String)
    fun getAuthToken(): Flow<String?>
    suspend fun deleteAuthToken()
    suspend fun saveLastUserLocation(location: String)
    fun getLastSavedUserLocation(): Flow<String?>
    suspend fun saveRideModeActive(value: Boolean)
    fun getRideModeActive(): Flow<Boolean?>
}
