package com.example.themoviedb.presentation.trending_screen

import com.example.themoviedb.data.remote.dto.Trending

data class TrendingState(
    val trending: Trending? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
