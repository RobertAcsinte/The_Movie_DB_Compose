package com.example.themoviedb.data.remote

import com.example.themoviedb.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("authentication/token/new")
    suspend fun requestToken(
        @Query("api_key") apiKey: String = API_KEY
    ): RequestTokenResponse

    @GET("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("request_token") requestToken: String,
    ): RequestTokenResponse

    @GET("authentication/session/new")
    suspend fun requestSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("request_token") requestToken: String
    ): SessionIdResponse

    @GET("authentication/guest_session/new")
    suspend fun requestSessionGuest(
        @Query("api_key") apiKey: String = API_KEY,
    ): SessionIdGuestResponse

    @GET("account")
    suspend fun getAccountInfo(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String
    ): UserResponse

    @GET("trending/all/day")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API_KEY
    ): MovieResponse

    @GET("trending/all/day")
    suspend fun getTrendingPagination(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): MovieResponse

    companion object {
        const val API_KEY = "c3decbd1e9df2262cba9028dbd8ee270"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}

