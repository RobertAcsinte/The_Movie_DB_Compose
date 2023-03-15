package com.example.themoviedb.domain.repository

import androidx.paging.PagingData
import com.example.themoviedb.data.remote.dto.*
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getUser(sessionId: String): Flow<Resource<User>>

    suspend fun login(username: String, password: String): Flow<Resource<SessionId>>
    suspend fun loginGuest(): Flow<Resource<SessionIdGuest>>

    suspend fun getTrending(): Flow<Resource<Trending>>

}