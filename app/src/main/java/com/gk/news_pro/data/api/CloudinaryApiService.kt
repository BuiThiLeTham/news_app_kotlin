package com.gk.news_pro.data.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryApiService {
    @Multipart
    @POST("api/external/upload-image")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): CloudinaryResponse
}

data class CloudinaryResponse(
    @SerializedName("secure_url") val secureUrl: String,
    @SerializedName("public_id") val publicId: String,
    @SerializedName("asset_id") val assetId: String
)