package com.apsl.glideapp.core.domain.auth

import com.apsl.glideapp.core.model.UserAuthState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveUserAuthenticationStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<UserAuthState> {
        return authRepository.isUserAuthenticated.map { isAuthenticated ->
            if (isAuthenticated) UserAuthState.Authenticated else UserAuthState.NotAuthenticated
        }
    }
}
