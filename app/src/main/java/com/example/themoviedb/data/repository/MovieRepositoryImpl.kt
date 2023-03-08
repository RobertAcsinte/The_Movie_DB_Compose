package com.example.themoviedb.data.repository

import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.User
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
): MovieRepository {

    override fun getUser(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            try{
                val results = api.getAccountInfo(sessionId = "25e0ffe136a125c8805dde4ff35da3829be72bd6")
                emit(Resource.Success(results))
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            } catch(e: HttpException) {
                emit(Resource.Error("Could not load data"))
                null
            }

        }
    }
}