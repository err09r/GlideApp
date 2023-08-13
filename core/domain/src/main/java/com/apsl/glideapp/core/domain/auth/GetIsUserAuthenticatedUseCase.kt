package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class GetIsUserAuthenticatedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = runCatching {
        authRepository.getIsUserAuthenticated()
    }
}
