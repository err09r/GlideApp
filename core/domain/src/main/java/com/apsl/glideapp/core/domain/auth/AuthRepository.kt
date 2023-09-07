package com.apsl.glideapp.core.domain.auth

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isUserAuthenticated: Flow<Boolean>
    suspend fun login(username: String, password: String): String
    suspend fun register(username: String, password: String): String
    suspend fun saveAuthToken(token: String)
    suspend fun deleteAuthToken()
}
