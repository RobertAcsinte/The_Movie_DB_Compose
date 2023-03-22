package com.example.themoviedb.data.repository

import com.example.themoviedb.data.local.TrendingDatabase
import com.example.themoviedb.data.mapper.toTrendingMovieEntity
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.*
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    val api: MovieApi,
    val db: TrendingDatabase
): MovieRepository {

    override fun getUser(sessionId: String): Flow<Resource<UserResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try{
                val user = api.getAccountInfo(sessionId = sessionId)
                emit(Resource.Success(user))
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

    override suspend fun login(username: String, password: String): Flow<Resource<SessionIdResponse>> {
        return flow {
            emit(Resource.Loading(true))
            val requestTokenResponse: RequestTokenResponse
            val sessionIdResponse: SessionIdResponse?
            try{
                requestTokenResponse = api.requestToken()
                requestTokenResponse.requestToken?.let { api.approveToken(username = username, password = password, requestToken = it) }
                sessionIdResponse = requestTokenResponse.requestToken?.let { api.requestSession(requestToken = it) }
                emit(Resource.Success(sessionIdResponse))
            } catch(e: IOException) {
                emit(Resource.Error(message = e.localizedMessage))
            } catch(e: HttpException) {
                var responseBody = e.response()?.errorBody()?.string()
                val statusMessage = JSONObject(responseBody).getString("status_message")
                emit(Resource.Error(message = e.localizedMessage, data = SessionIdResponse(success = false, sessionId = null, statusMessage = statusMessage)))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun loginGuest(): Flow<Resource<SessionIdGuestResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val sessionIdGuestResponse = api.requestSessionGuest()
                emit(Resource.Success(sessionIdGuestResponse))
            } catch(e: IOException) {
                emit(Resource.Error(message = e.localizedMessage))
            } catch(e: HttpException) {
                emit(Resource.Error(e.localizedMessage))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getTrending(): Flow<Resource<MovieResponse>> {
        return flow {
            var id = 0
            emit(Resource.Loading(true))
            try {
                val trending = api.getTrending()
                emit(Resource.Success(trending))
                val trendingDao = trending.results.map {
                    it.toTrendingMovieEntity()
                }
                trendingDao.map {
                    it.page = trending.page
                    it.fetchedOrder = id
                    id++
                }
                trendingDao.forEach {
                    println("sloboz " + it.name + it.fetchedOrder)
                }
                withContext(Dispatchers.IO) {
                    db.trendingMovieDao().insertAll(trendingDao)
                }
            } catch(e: IOException) {
                emit(Resource.Error(message = e.localizedMessage))
            } catch(e: HttpException) {
                emit(Resource.Error(e.localizedMessage))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }

//    override fun getTrendingDb(): Flow<Resource<List<TrendingMovieEntity>>> {
//        return flow {
//            emit(Resource.Loading(true))
//            try {
//                db.trendingMovieDao().getAll().collect() {
//                    emit(Resource.Success(it))
//                    emit(Resource.Loading(false))
//                }
//                emit(Resource.Loading(true))
//            } catch(e: IOException) {
//                emit(Resource.Error(message = e.localizedMessage))
//            } catch(e: HttpException) {
//                emit(Resource.Error(e.localizedMessage))
//            }
//            finally {
//                emit(Resource.Loading(false))
//            }
//        }
//    }



}