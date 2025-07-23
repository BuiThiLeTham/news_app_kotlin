package com.gk.newsapp.data.api

import com.gk.news_pro.data.model.Country
import com.gk.news_pro.data.model.RadioStation
import com.gk.news_pro.data.model.Tag
import retrofit2.http.GET
import retrofit2.http.Query

interface RadioApiService {
    @GET("api/external/radio/stations/bycountry")
    suspend fun getStationsByCountry(
        @Query("country") country: String,
        @Query("limit") limit: Int = 100
    ): List<RadioStation>

    @GET("api/external/radio/stations/bytag")
    suspend fun getStationsByTag(
        @Query("tag") tag: String,
        @Query("limit") limit: Int = 100
    ): List<RadioStation>

    @GET("api/external/radio/stations/byuuid")
    suspend fun getStationByUuid(
        @Query("uuid") uuid: String
    ): List<RadioStation>

    @GET("api/external/radio/stations/search")
    suspend fun searchStations(
        @Query("name") name: String,
        @Query("country") country: String = "",
        @Query("tag") tag: String = "",
        @Query("limit") limit: Int = 100
    ): List<RadioStation>

    @GET("api/external/radio/countries")
    suspend fun getCountries(): List<Country>

    @GET("api/external/radio/tags")
    suspend fun getTags(): List<Tag>
}