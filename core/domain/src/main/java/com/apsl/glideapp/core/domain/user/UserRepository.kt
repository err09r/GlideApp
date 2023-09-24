package com.apsl.glideapp.core.domain.user

import com.apsl.glideapp.core.domain.home.User

interface UserRepository {
    suspend fun getUser(): User?
}
