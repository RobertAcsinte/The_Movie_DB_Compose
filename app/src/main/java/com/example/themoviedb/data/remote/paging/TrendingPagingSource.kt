package com.example.themoviedb.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.data.remote.dto.Movie
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class TrendingPagingSource(
    private val api: MovieApi,
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        //if anchorPosition is not null, it means that the user has scrolled up or down
        return state.anchorPosition?.let { anchorPosition ->
            //get the current page of data displayed
            val anchorPage = state.closestPageToPosition(anchorPosition)
            //if the previous key (user scrolled up), return previous + 1
            //if the next key (user scrolled down), return previous -1
            //the reason why is -1 and +1 is because the user has
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getTrendingPagination(page = page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: HttpException) {
            var responseBody = e.response()?.errorBody()?.string()
            val statusMessage = JSONObject(responseBody).getString("status_message")
            LoadResult.Error(Exception(statusMessage))
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

