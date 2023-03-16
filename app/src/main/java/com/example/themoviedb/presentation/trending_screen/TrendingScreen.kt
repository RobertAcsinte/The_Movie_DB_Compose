package com.example.themoviedb.presentation.trending_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel(),
    navigate: () -> Unit
) {
    val state by viewModel.trendingState.collectAsState()
    val trendingPagination = viewModel.trendingPagingList.collectAsLazyPagingItems()
    val statePag = trendingPagination.loadState.refresh
    val pullRefreshState = rememberPullRefreshState(refreshing = statePag is LoadState.Loading, { trendingPagination.refresh()})



    Box(Modifier.pullRefresh(pullRefreshState).fillMaxSize()){
        LazyColumn {
            items(
                items = trendingPagination,
                key = null
            ) { movie ->
                if (movie != null) {
                    TrendingItem(title = movie.name, posterPath = movie.posterPath, backgroundPath = movie.backdropPath)
                }
            }
        }
        PullRefreshIndicator(statePag is LoadState.Loading, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }


        when(statePag) {
            is LoadState.Loading -> {
            }
            is LoadState.Error -> {
                statePag.error.message?.let {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it,
                        )
                    }
                }
            }
            is LoadState.NotLoading -> {
            }
        }
        when(val state = trendingPagination.loadState.append) {
            is LoadState.Loading -> {
            }
            is LoadState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    state.error.message?.let {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = it
                        )
                    }
                    CircularProgressIndicator()
                }
            }
            is LoadState.NotLoading -> {
            }
        }

    Button(
        onClick =  navigate
    ) {
        Text("Go account")
    }
}