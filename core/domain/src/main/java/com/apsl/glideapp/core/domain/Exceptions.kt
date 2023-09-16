package com.apsl.glideapp.core.domain

class InvalidAuthTokenException(
    message: String = "Retrieved token is invalid"
) : Exception(message)

class AddressDecoderException(message: String) : Exception(message)

