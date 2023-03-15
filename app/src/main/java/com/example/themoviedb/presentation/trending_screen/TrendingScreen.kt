package com.example.themoviedb.presentation.trending_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.themoviedb.R


@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel(),
    navigate: () -> Unit
) {
    val state by viewModel.trendingState.collectAsState()
    val trendingPagination = viewModel.trendingPagingList.collectAsLazyPagingItems()
    val statePag =   trendingPagination.loadState.refresh

    LazyColumn() {
        items(
            items = trendingPagination,
            key = null
        ) { movie ->
            if (movie != null) {
                TrendingItem(title = movie.name)
            }
        }
    }


        when(statePag) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
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
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = stringResource(id = R.string.loading_data)
                    )
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
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