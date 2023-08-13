package com.apsl.glideapp.core.domain.user

import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = runCatching {
        userRepository.getUser()
    }
}
