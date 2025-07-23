package com.gk.news_pro.data.repository

import android.util.Log
//import com.gk.news_pro.data.api.RetrofitClient
import com.gk.news_pro.data.api.UserApiService
import com.gk.news_pro.data.api.UserUpdateRequest
import com.gk.news_pro.data.model.News
import com.gk.news_pro.data.model.RadioStation
import com.gk.news_pro.data.model.User
import com.gk.newsapp.data.api.RetrofitClient
import retrofit2.HttpException

class UserService(private val token: String?) {
    private val userApiService = RetrofitClient.createService(UserApiService::class.java, token)
    private val TAG = "UserService"

    suspend fun addUser(uid: String, user: User) {
        try {
            userApiService.addUser(user.copy(uid = uid))
            Log.d(TAG, "addUser: Successfully saved user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "addUser: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "addUser: Failed to save user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun getUser(uid: String): User? {
        return try {
            val user = userApiService.getUser(uid)
            Log.d(TAG, "getUser: Retrieved user $uid: ${user?.email}")
            user
        } catch (e: HttpException) {
            Log.e(TAG, "getUser: Failed with status ${e.code()}: ${e.message()}", e)
            null
        } catch (e: Exception) {
            Log.e(TAG, "getUser: Failed to retrieve user $uid: ${e.message}", e)
            null
        }
    }

    suspend fun getAllUsers(): List<User> {
        return try {
            val users = userApiService.getAllUsers()
            Log.d(TAG, "getAllUsers: Retrieved ${users.size} users")
            users
        } catch (e: HttpException) {
            Log.e(TAG, "getAllUsers: Failed with status ${e.code()}: ${e.message()}", e)
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "getAllUsers: Failed to retrieve users: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun updateUser(uid: String, username: String?, email: String?, avatar: String?, password: String?) {
        try {
            userApiService.updateUser(uid, UserUpdateRequest(username, email, avatar, password))
            Log.d(TAG, "updateUser: Successfully updated user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "updateUser: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "updateUser: Failed to update user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun deleteUser(uid: String) {
        try {
            userApiService.deleteUser(uid)
            Log.d(TAG, "deleteUser: Successfully deleted user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "deleteUser: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "deleteUser: Failed to delete user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun addFavoriteNews(uid: String, news: News) {
        try {
            userApiService.addFavoriteNews(uid, news)
            Log.d(TAG, "addFavoriteNews: Successfully added news ${news.article_id} for user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "addFavoriteNews: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "addFavoriteNews: Failed to add news for user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun getFavoriteNews(uid: String): List<News> {
        return try {
            val newsList = userApiService.getFavoriteNews(uid)
            Log.d(TAG, "getFavoriteNews: Retrieved ${newsList.size} favorite news for user $uid")
            newsList
        } catch (e: HttpException) {
            Log.e(TAG, "getFavoriteNews: Failed with status ${e.code()}: ${e.message()}", e)
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "getFavoriteNews: Failed to retrieve favorite news for user $uid: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun removeFavoriteNews(uid: String, newsId: String) {
        try {
            userApiService.removeFavoriteNews(uid, newsId)
            Log.d(TAG, "removeFavoriteNews: Successfully removed news $newsId for user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "removeFavoriteNews: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "removeFavoriteNews: Failed to remove news $newsId for user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun addFavoriteRadioStation(uid: String, station: RadioStation) {
        try {
            userApiService.addFavoriteRadioStation(uid, station)
            Log.d(TAG, "addFavoriteRadioStation: Successfully added station ${station.stationuuid} for user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "addFavoriteRadioStation: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "addFavoriteRadioStation: Failed to add station for user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun getFavoriteRadioStations(uid: String): List<RadioStation> {
        return try {
            val stationList = userApiService.getFavoriteRadioStations(uid)
            Log.d(TAG, "getFavoriteRadioStations: Retrieved ${stationList.size} favorite stations for user $uid")
            stationList
        } catch (e: HttpException) {
            Log.e(TAG, "getFavoriteRadioStations: Failed with status ${e.code()}: ${e.message()}", e)
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "getFavoriteRadioStations: Failed to retrieve favorite stations for user $uid: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun removeFavoriteRadioStation(uid: String, stationId: String) {
        try {
            userApiService.removeFavoriteRadioStation(uid, stationId)
            Log.d(TAG, "removeFavoriteRadioStation: Successfully removed station $stationId for user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "removeFavoriteRadioStation: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "removeFavoriteRadioStation: Failed to remove station $stationId for user $uid: ${e.message}", e)
            throw e
        }
    }

    suspend fun updateFavoriteTopics(uid: String, topics: Map<String, Int>) {
        try {
            userApiService.updateFavoriteTopics(uid, topics)
            Log.d(TAG, "updateFavoriteTopics: Successfully updated topics for user $uid")
        } catch (e: HttpException) {
            Log.e(TAG, "updateFavoriteTopics: Failed with status ${e.code()}: ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "updateFavoriteTopics: Failed to update topics for user $uid: ${e.message}", e)
            throw e
        }
    }
}