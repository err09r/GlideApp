package com.apsl.glideapp.core.domain.home

data class User(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val totalDistance: Double,
    val totalRides: Int,
    val balance: Double
)
