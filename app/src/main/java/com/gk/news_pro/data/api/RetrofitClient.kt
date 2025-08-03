package com.gk.news_pro.data.api

import com.gk.news_pro.data.service.GeminiApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/" // URL cho API nội bộ
    private const val NEWS_BASE_URL = "https://newsdata.io/api/1/"
    private const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val RADIO_BASE_URL = "https://de1.api.radio-browser.info/"
    private const val HEYGEN_BASE_URL = "https://api.heygen.com/"

    // Shared OkHttpClient with timeouts and logging
    private fun getClient(token: String? = null): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
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

    // Tạo dịch vụ cho API nội bộ
    fun <T> createService(serviceClass: Class<T>, token: String? = null): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    // Tạo dịch vụ cho Cloudinary
    fun createCloudinaryService(cloudName: String): CloudinaryApiService {
        val cloudinaryUrl = "https://api.cloudinary.com/v1_1/$cloudName/"
        return Retrofit.Builder()
            .baseUrl(cloudinaryUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApiService::class.java)
    }

    // Tạo dịch vụ cho News
    fun createNewsService(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
        val newsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Tạo dịch vụ cho Gemini
    fun createGeminiService(): GeminiApiService {
        return Retrofit.Builder()
            .baseUrl(GEMINI_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }

    // Tạo dịch vụ cho Radio
    fun createRadioService(): RadioApiService {
        return Retrofit.Builder()
            .baseUrl(RADIO_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RadioApiService::class.java)
    }

    // Tạo dịch vụ cho HeyGen
    fun createHeyGenService(): HeyGenApiService {
        return Retrofit.Builder()
            .baseUrl(HEYGEN_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeyGenApiService::class.java)
    }

        val radioRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RADIO_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
        val geminiRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(GEMINI_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
        val heyGenRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(HEYGEN_BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}