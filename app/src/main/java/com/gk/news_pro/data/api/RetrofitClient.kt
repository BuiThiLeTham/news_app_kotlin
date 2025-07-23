package com.gk.newsapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/" // Base URL cho emulator

    private fun getClient(token: String? = null): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().apply {
                    if (token != null) {
                        addHeader("Authorization", "Bearer $token")
                    }
                }.build()
                chain.proceed(request)
            }
            .retryOnConnectionFailure(true)
            .build()
    }

    fun <T> createService(serviceClass: Class<T>, token: String? = null): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}