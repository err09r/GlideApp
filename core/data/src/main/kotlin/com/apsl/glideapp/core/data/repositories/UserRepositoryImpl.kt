package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.common.dto.UserDto
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.datastore.CurrentUser
import com.apsl.glideapp.core.domain.user.UserRepository
import com.apsl.glideapp.core.model.User
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime

class UserRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val appDataStore: AppDataStore
) : UserRepository {

    override suspend fun getUser(): User? {
        val userDto = runCatching { api.getUser() }.getOrNull()
        if (userDto != null) {
            val user = userDto.toUser()
            appDataStore.saveCurrentUser(user.toCurrentUser())
            return user
        }

        return appDataStore.currentUser.firstOrNull()?.toUser()
    }

    private fun UserDto.toUser(): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistance = totalDistance,
            totalRides = totalRides,
            balance = balance
        )
    }

    private fun CurrentUser.toUser(): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistance = totalDistance,
            totalRides = totalRides,
            balance = balance
        )
    }

    private fun User.toCurrentUser(): CurrentUser {
        return CurrentUser(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistance = totalDistance,
            totalRides = totalRides,
            balance = balance,
            savingDateTime = LocalDateTime.now()
        )
    }
}
