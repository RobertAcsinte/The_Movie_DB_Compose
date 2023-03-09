package com.example.themoviedb.data.repository

import android.util.Log
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.RequestToken
import com.example.themoviedb.data.remote.dto.SessionId
import com.example.themoviedb.data.remote.dto.User
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
    private val api: MovieApi,
): MovieRepository {

    override fun getUser(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            try{
                val user = api.getAccountInfo(sessionId = "25e0ffe136a125c8805dde4ff35da3829be72bd6")
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
            var requestValidate: RequestToken? = null
            val sessionIdResponse: SessionId?
            try{
                requestTokenResponse = api.requestToken()
                requestValidate = requestTokenResponse.requestToken?.let { api.approveToken(username = username, password = password, requestToken = it) }
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
}