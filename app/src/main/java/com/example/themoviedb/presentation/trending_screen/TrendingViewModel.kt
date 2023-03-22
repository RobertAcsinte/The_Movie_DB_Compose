package com.example.themoviedb.presentation.trending_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themoviedb.data.paging.TrendingMoviesRemoteMediator
import com.example.themoviedb.data.paging.TrendingPagingSource
import com.example.themoviedb.data.repository.MovieRepositoryImpl
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: MovieRepository,
): ViewModel(){

    private val _trendingState = MutableStateFlow(TrendingState())
    val trendingState = _trendingState.asStateFlow()


    @OptIn(ExperimentalPagingApi::class)
    val trendingPagingList =  Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            (repository as MovieRepositoryImpl).db.trendingMovieDao().getAll()
        },
        remoteMediator = TrendingMoviesRemoteMediator((repository as MovieRepositoryImpl).api, (repository as MovieRepositoryImpl).db)
    ).flow.cachedIn(viewModelScope)



    fun getTrending(){
        viewModelScope.launch {
            repository.getTrending().collect {
                when(it){
                    is Resource.Success -> {
                        _trendingState.value = _trendingState.value.copy(trending = it.data)
                    }
                    is Resource.Error -> {
                        _trendingState.value = _trendingState.value.copy(error = it.message)
                    }
                    is Resource.Loading -> {
                        _trendingState.value = _trendingState.value.copy(isLoading = it.isLoading)
                    }
                }
            }
        }
    }


//    fun getTrendingDb(){
//        viewModelScope.launch {
//            repository.getTrendingDb().collect {
//                when(it){
//                    is Resource.Success -> {
////                        _trendingState.value = _trendingState.value.copy(trending = it.data)
//                        println("sloboz " + it.data)
//                    }
//                    is Resource.Error -> {
////                        _trendingState.value = _trendingState.value.copy(error = it.message)
//                    }
//                    is Resource.Loading -> {
////                        _trendingState.value = _trendingState.value.copy(isLoading = it.isLoading)
//                        println("sloboz loading" + it.isLoading)
//                    }
//                }
//            }
//        }
//    }
}