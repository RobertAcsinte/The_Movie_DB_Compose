package com.example.themoviedb.presentation.trending_screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.themoviedb.data.remote.dto.Movie
import com.example.themoviedb.data.remote.paging.TrendingPagingSource
import com.example.themoviedb.data.repository.MovieRepositoryImpl
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: MovieRepository,
): ViewModel(){

    private val _trendingState = MutableStateFlow(TrendingState())
    val trendingState = _trendingState.asStateFlow()

    val trendingPagingList = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            TrendingPagingSource((repository as MovieRepositoryImpl).api)
        }
    ).flow.cachedIn(viewModelScope)

    init {
        //getTrending()
    }

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

}