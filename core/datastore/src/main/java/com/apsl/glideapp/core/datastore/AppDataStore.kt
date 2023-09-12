package com.apsl.glideapp.core.datastore

import com.apsl.glideapp.common.models.Coordinates
import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    val authToken: Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun deleteAuthToken()
    val lastUserLocation: Flow<Coordinates?>
    suspend fun saveLastUserLocation(location: Coordinates): Coordinates?
    val isRideModeActive: Flow<Boolean?>
    suspend fun saveRideModeActive(value: Boolean): Boolean?
    val unlockDistance: Flow<Double?>
    suspend fun saveUnlockDistance(distance: Double): Double?
}
