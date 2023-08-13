package com.apsl.glideapp.core.domain.auth

interface AuthRepository {
    suspend fun login(username: String, password: String): String
    suspend fun register(username: String, password: String): String
    suspend fun saveAuthToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun getIsUserAuthenticated(): Boolean
}
