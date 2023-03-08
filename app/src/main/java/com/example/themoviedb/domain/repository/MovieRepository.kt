package com.example.themoviedb.domain.repository

import com.example.themoviedb.data.remote.dto.User
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getUser(): Flow<Resource<User>>
}