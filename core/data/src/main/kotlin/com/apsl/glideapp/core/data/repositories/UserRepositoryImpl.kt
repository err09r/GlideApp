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
        val walletVisited = appDataStore.walletVisited.firstOrNull() ?: false
        val userDto = runCatching { api.getUser() }.getOrNull()

        if (userDto != null) {
            val user = userDto.toUser(walletVisited = walletVisited)
            appDataStore.saveCurrentUser(user.toCurrentUser())
            return user
        }

        return appDataStore.currentUser.firstOrNull()?.toUser(walletVisited = walletVisited)
    }

    private fun UserDto.toUser(walletVisited: Boolean): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistanceMeters = totalDistance,
            totalRides = totalRides,
            balance = balance,
            walletVisited = walletVisited
        )
    }

    private fun CurrentUser.toUser(walletVisited: Boolean): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistanceMeters = totalDistance,
            totalRides = totalRides,
            balance = balance,
            walletVisited = walletVisited
        )
    }

    private fun User.toCurrentUser(): CurrentUser {
        return CurrentUser(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            totalDistance = totalDistanceMeters,
            totalRides = totalRides,
            balance = balance,
            savingDateTime = LocalDateTime.now()
        )
    }

    override suspend fun saveWalletVisited(value: Boolean) {
        appDataStore.saveWalletVisited(value)
    }
}
