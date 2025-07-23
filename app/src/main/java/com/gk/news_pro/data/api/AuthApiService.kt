package com.gk.news_pro.data.api

import com.gk.news_pro.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/register")
    suspend fun register(@Body user: User)

    @POST("api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)