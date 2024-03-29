package com.apsl.glideapp.core.model

data class User(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val totalDistanceMeters: Double,
    val totalRides: Int,
    val balance: Double,
    val walletVisited: Boolean
)
