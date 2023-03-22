package com.example.themoviedb.presentation.trending_screen

import com.example.themoviedb.data.remote.dto.MovieResponse

data class TrendingState(
    val trending: MovieResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
