package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String, password: String) = runCatching {
        val token = authRepository.register(username = username, password = password)
        if (token.isBlank()) {
            throw InvalidAuthTokenException()
        }
        authRepository.saveAuthToken(token)
    }
}
