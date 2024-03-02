package com.apsl.glideapp.core.domain.auth

import com.apsl.glideapp.core.domain.user.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = runCatching {
        authRepository.logOut()
        userRepository.saveWalletVisited(false)
    }
}
