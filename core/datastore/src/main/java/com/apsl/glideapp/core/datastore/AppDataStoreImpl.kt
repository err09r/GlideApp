package com.apsl.glideapp.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val USER_AUTH_TOKEN = stringPreferencesKey("user_auth_token")
internal val LAST_USER_LOCATION = stringPreferencesKey("last_user_location")
internal val WAS_LOCATION_REQUEST_RATIONALE_SHOWN =
    booleanPreferencesKey("was_location_request_rationale_shown")

class AppDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppDataStore {

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { it[USER_AUTH_TOKEN] = token }
    }

    override fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { it[USER_AUTH_TOKEN] }
    }

    override suspend fun deleteAuthToken() {
        dataStore.edit { it.remove(USER_AUTH_TOKEN) }
    }

    override suspend fun saveLastUserLocation(location: String) {
        dataStore.edit { it[LAST_USER_LOCATION] = location }
    }

    override fun getLastSavedUserLocation(): Flow<String?> {
        return dataStore.data.map { it[LAST_USER_LOCATION] }
    }

    override suspend fun saveLocationRequestRationaleWasShown() {
        dataStore.edit { it[WAS_LOCATION_REQUEST_RATIONALE_SHOWN] = true }
    }

    override val wasLocationRequestRationaleShown: Flow<Boolean?>
        get() = dataStore.data.map { it[WAS_LOCATION_REQUEST_RATIONALE_SHOWN] }
}
