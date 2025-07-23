package com.gk.news_pro.data.api

import com.gk.news_pro.data.model.News
import com.gk.news_pro.data.model.RadioStation
import com.gk.news_pro.data.model.User
import retrofit2.http.*

interface UserApiService {
    @POST("api/users")
    suspend fun addUser(@Body user: User)

    @GET("api/users/{uid}")
    suspend fun getUser(@Path("uid") uid: String): User?

    @GET("api/users")
    suspend fun getAllUsers(): List<User>

    @PUT("api/users/{uid}")
    suspend fun updateUser(
        @Path("uid") uid: String,
        @Body updates: UserUpdateRequest
    )

    @DELETE("api/users/{uid}")
    suspend fun deleteUser(@Path("uid") uid: String)

    @POST("api/users/{uid}/favorite-news")
    suspend fun addFavoriteNews(@Path("uid") uid: String, @Body news: News)

    @GET("api/users/{uid}/favorite-news")
    suspend fun getFavoriteNews(@Path("uid") uid: String): List<News>

    @DELETE("api/users/{uid}/favorite-news/{newsId}")
    suspend fun removeFavoriteNews(@Path("uid") uid: String, @Path("newsId") newsId: String)

    @POST("api/users/{uid}/favorite-radio-stations")
    suspend fun addFavoriteRadioStation(@Path("uid") uid: String, @Body station: RadioStation)

    @GET("api/users/{uid}/favorite-radio-stations")
    suspend fun getFavoriteRadioStations(@Path("uid") uid: String): List<RadioStation>

    @DELETE("api/users/{uid}/favorite-radio-stations/{stationId}")
    suspend fun removeFavoriteRadioStation(@Path("uid") uid: String, @Path("stationId") stationId: String)

    @PUT("api/users/{uid}/favorite-topics")
    suspend fun updateFavoriteTopics(@Path("uid") uid: String, @Body topics: Map<String, Int>)
}

data class UserUpdateRequest(
    val username: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val password: String? = null
)