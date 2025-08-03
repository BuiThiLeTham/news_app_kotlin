package com.gk.news_pro.data.api

import com.gk.news_pro.data.model.News
import com.gk.news_pro.data.model.NewsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("api/external/news")
    suspend fun getNews(
        @Query("query") query: String? = null,
        @Query("category") category: String? = null,
        @Query("country") country: String? = null,
        @Query("language") language: String? = "vi"
    ): NewsResponse

    @GET("api/news/saved")
    suspend fun getSavedNews(): List<News>

    @GET("api/news/saved/{link}")
    suspend fun getSavedNewsByLink(@Path("link") link: String): News?

    @POST("api/news/save")
    suspend fun saveNews(@Body news: News)

    @DELETE("api/news/saved/{link}")
    suspend fun deleteSavedNews(@Path("link") link: String)
}