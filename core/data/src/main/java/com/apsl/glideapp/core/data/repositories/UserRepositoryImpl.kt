package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.core.domain.home.User
import com.apsl.glideapp.core.domain.user.UserRepository
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: GlideApi) : UserRepository {

    override suspend fun getUser(): User {
        val dto = api.getUser()
        return User(
            id = dto.id,
            username = dto.username,
            firstName = dto.firstName,
            lastName = dto.lastName,
            totalDistance = dto.totalDistance,
            totalRides = dto.totalRides,
            balance = dto.balance
        )
    }
}
