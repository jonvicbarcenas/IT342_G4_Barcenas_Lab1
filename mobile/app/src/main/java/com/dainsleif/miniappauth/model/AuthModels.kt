package com.dainsleif.miniappauth.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String?,
    val userId: Int?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val message: String?
)
