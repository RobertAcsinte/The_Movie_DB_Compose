package com.example.themoviedb.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.*
import com.example.themoviedb.data.remote.paging.TrendingPagingSource
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    val api: MovieApi,
): MovieRepository {

    override fun getUser(sessionId: String): Flow<Resource<User>> {
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

    override suspend fun login(username: String, password: String): Flow<Resource<SessionId>> {
        return flow {
            emit(Resource.Loading(true))
            val requestTokenResponse: RequestToken
            val sessionIdResponse: SessionId?
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
                emit(Resource.Error(message = e.localizedMessage, data = SessionId(success = false, sessionId = null, statusMessage = statusMessage)))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun loginGuest(): Flow<Resource<SessionIdGuest>> {
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

    override suspend fun getTrending(): Flow<Resource<Trending>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val trending = api.getTrending()
                emit(Resource.Success(trending))
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
}