package com.dainsleif.miniappauth.network

import com.dainsleif.miniappauth.model.AuthResponse
import com.dainsleif.miniappauth.model.LoginRequest
import com.dainsleif.miniappauth.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>
}
