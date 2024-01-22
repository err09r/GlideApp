package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.dto.LoginRequest
import com.apsl.glideapp.common.dto.RegisterRequest
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.auth.AuthRepository
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val appDataStore: AppDataStore
) : AuthRepository {

    override val isUserAuthenticated: Flow<Boolean> = appDataStore.authToken.map { it != null }

    override suspend fun logIn(username: String, password: String): String {
        val authResponse = api.login(
            body = LoginRequest(username = username, password = password)
        )
        return authResponse.token
    }

    override suspend fun register(username: String, password: String): String {
        val authResponse = api.register(
            body = RegisterRequest(username = username, password = password)
        )
        return authResponse.token
    }

    override suspend fun saveAuthToken(token: String) {
        appDataStore.saveAuthToken(token)
    }

    override suspend fun logOut() {
        appDataStore.deleteAuthToken()
    }
}
