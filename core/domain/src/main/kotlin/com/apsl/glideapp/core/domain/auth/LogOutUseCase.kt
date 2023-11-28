package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = runCatching {
        authRepository.logOut()
    }
}
