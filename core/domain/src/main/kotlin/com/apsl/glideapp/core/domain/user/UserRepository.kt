package com.apsl.glideapp.core.domain.user

import com.apsl.glideapp.core.model.User

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun saveWalletVisited(value: Boolean)
}
