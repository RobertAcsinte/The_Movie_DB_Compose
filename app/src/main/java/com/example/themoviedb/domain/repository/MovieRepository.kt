package com.example.themoviedb.domain.repository

import com.example.themoviedb.data.remote.dto.*
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getUser(sessionId: String): Flow<Resource<UserResponse>>

    suspend fun login(username: String, password: String): Flow<Resource<SessionIdResponse>>
    suspend fun loginGuest(): Flow<Resource<SessionIdGuestResponse>>

    suspend fun getTrending(): Flow<Resource<MovieResponse>>

//    fun getTrendingDb(): Flow<Resource<List<TrendingMovieEntity>>>

}