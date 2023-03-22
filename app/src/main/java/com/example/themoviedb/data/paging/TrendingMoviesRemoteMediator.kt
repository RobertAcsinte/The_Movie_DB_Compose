package com.example.themoviedb.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.themoviedb.data.local.TrendingDatabase
import com.example.themoviedb.data.local.entity.RemoteKeysEntity
import com.example.themoviedb.data.local.entity.TrendingMovieEntity
import com.example.themoviedb.data.mapper.toTrendingMovieEntity
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.MovieDto
import com.example.themoviedb.data.remote.dto.MovieResponse
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class TrendingMoviesRemoteMediator (
    private val moviesApiService: MovieApi,
    private val moviesDatabase: TrendingDatabase,
): RemoteMediator<Int, TrendingMovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (moviesDatabase.remoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TrendingMovieEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = moviesApiService.getTrendingPagination(page = page)

            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.remoteKeysDao().clearRemoteKeys()
                    moviesDatabase.trendingMovieDao().clearAllMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeysEntity(movieID = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }
                val movieEntity = movies.map {
                    it.toTrendingMovieEntity()
                }

                var index: Int? = moviesDatabase.trendingMovieDao().getLastFetchedOrder()
                if(index == null) {
                    index = 1
                }
                moviesDatabase.remoteKeysDao().insertAll(remoteKeys)
                moviesDatabase.trendingMovieDao().insertAll(movieEntity.onEachIndexed {
                        _, movie ->
                    movie.page = page
                    movie.fetchedOrder = index
                    index++
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TrendingMovieEntity>): RemoteKeysEntity? {
        state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
            }
        }
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                moviesDatabase.remoteKeysDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TrendingMovieEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            moviesDatabase.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TrendingMovieEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            moviesDatabase.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }
}