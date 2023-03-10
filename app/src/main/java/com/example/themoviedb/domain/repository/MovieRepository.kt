package com.example.themoviedb.domain.repository

import com.example.themoviedb.data.remote.dto.SessionId
import com.example.themoviedb.data.remote.dto.SessionIdGuest
import com.example.themoviedb.data.remote.dto.User
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getUser(sessionId: String): Flow<Resource<User>>

    suspend fun login(username: String, password: String): Flow<Resource<SessionId>>

    suspend fun loginGuest(): Flow<Resource<SessionIdGuest>>
}