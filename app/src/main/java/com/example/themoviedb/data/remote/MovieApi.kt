package com.example.themoviedb.data.remote

import com.example.themoviedb.data.remote.dto.RequestToken
import com.example.themoviedb.data.remote.dto.SessionId
import com.example.themoviedb.data.remote.dto.User
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("authentication/token/new")
    suspend fun requestToken(
        @Query("api_key") apiKey: String = API_KEY
    ): RequestToken

    @GET("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("request_token") requestToken: String,
    ): RequestToken

    @GET("authentication/session/new")
    suspend fun requestSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("request_token") requestToken: String
    ): SessionId

    @GET("account")
    suspend fun getAccountInfo(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String
    ): User

    companion object {
        const val API_KEY = "c3decbd1e9df2262cba9028dbd8ee270"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}

