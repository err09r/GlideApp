package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(username: String, password: String) = runCatching {
        val token = authRepository.logIn(username = username, password = password)
        if (token.isBlank()) {
            throw InvalidAuthTokenException()
        }
        authRepository.saveAuthToken(token)
    }
}
