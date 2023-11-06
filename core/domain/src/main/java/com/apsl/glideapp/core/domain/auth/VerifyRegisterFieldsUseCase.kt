package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class VerifyRegisterFieldsUseCase @Inject constructor(
    private val checkIfUsernameValidUseCase: CheckIfUsernameValidUseCase,
    private val checkIfPasswordValidUseCase: CheckIfPasswordValidUseCase
) {
    operator fun invoke(
        username: String,
        password: String,
        repeatPassword: String
    ): RegisterFieldsVerificationResult {
        if (password != repeatPassword) return RegisterFieldsVerificationResult.PasswordsDoNotMatch
        val isUsernameValid = checkIfUsernameValidUseCase(username)
        val isPasswordValid = checkIfPasswordValidUseCase(password)
        return when {
            isUsernameValid && isPasswordValid -> RegisterFieldsVerificationResult.Success
            isUsernameValid && !isPasswordValid -> RegisterFieldsVerificationResult.InvalidPasswordFormat
            else -> RegisterFieldsVerificationResult.InvalidUsernameFormat
        }
    }
}

enum class RegisterFieldsVerificationResult {
    Success, InvalidUsernameFormat, InvalidPasswordFormat, PasswordsDoNotMatch
}

val RegisterFieldsVerificationResult.isError get() = this != RegisterFieldsVerificationResult.Success
