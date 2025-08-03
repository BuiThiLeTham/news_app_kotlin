package com.gk.news_pro.data.repository

import android.content.Context
import android.util.Log
import com.gk.news_pro.data.api.AuthApiService
import com.gk.news_pro.data.api.AuthRequest
import com.gk.news_pro.data.api.RetrofitClient

import com.gk.news_pro.data.model.User


class AuthService(private val context: Context) {
    private val authApiService = RetrofitClient.createService(AuthApiService::class.java)
    private val TAG = "AuthService"
    private val PREFS_NAME = "NewsProPrefs"
    private val KEY_TOKEN = "jwt_token"
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun register(user: User) {
        try {
            authApiService.register(user)
            Log.d(TAG, "register: Successfully registered user ${user.id}")
            login(user.email, user.password)?.let { token ->
                with(prefs.edit()) {
                    putString(KEY_TOKEN, token)
                    apply()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "register: Failed to register user ${user.id}: ${e.message}", e)
            throw e
        }
    }

    suspend fun login(email: String, password: String): String? {
        return try {
            val response = authApiService.login(AuthRequest(email, password))
            val token = response.token
            with(prefs.edit()) {
                putString(KEY_TOKEN, token)
                apply()
            }
            Log.d(TAG, "login: Successfully logged in user with email $email")
            token
        } catch (e: Exception) {
            Log.e(TAG, "login: Failed to login with email $email: ${e.message}", e)
            null
        }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun logout() {
        with(prefs.edit()) {
            remove(KEY_TOKEN)
            apply()
        }
        Log.d(TAG, "logout: User logged out")
    }
}