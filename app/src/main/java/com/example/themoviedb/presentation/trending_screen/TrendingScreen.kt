package com.example.themoviedb.presentation.trending_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrendingScreen(
    viewModel: TrendingViewModel = hiltViewModel(),
    navigate: () -> Unit
) {
    val state by viewModel.trendingState.collectAsState()
    val trendingPagination = viewModel.trendingPagingList.collectAsLazyPagingItems()
    val statePag = trendingPagination.loadState.refresh
    val pullRefreshState = rememberPullRefreshState(refreshing = statePag is LoadState.Loading, onRefresh =  { trendingPagination.refresh()})


    LaunchedEffect(key1 = statePag ) {

    }


    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()){
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

//    LazyColumn {
//        items(
//            items = trendingPagination
//        ) { movie ->
//            movie?.let {
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    if (movie.posterPath != null) {
//                        var isImageLoading by remember { mutableStateOf(false) }
//
//                        val painter = rememberAsyncImagePainter(
//                            model = "https://image.tmdb.org/t/p/w154" + movie.posterPath,
//                        )
//
//                        isImageLoading = when(painter.state) {
//                            is AsyncImagePainter.State.Loading -> true
//                            else -> false
//                        }
//
//                        Box (
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Image(
//                                modifier = Modifier
//                                    .padding(horizontal = 6.dp, vertical = 3.dp)
//                                    .height(115.dp)
//                                    .width(77.dp)
//                                    .clip(RoundedCornerShape(8.dp)),
//                                painter = painter,
//                                contentDescription = "Poster Image",
//                                contentScale = ContentScale.FillBounds,
//                            )
//
//                            if (isImageLoading) {
//                                androidx.compose.material.CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .padding(horizontal = 6.dp, vertical = 3.dp),
//                                    color = MaterialTheme.colors.primary,
//                                )
//                            }
//                        }
//                    }
//                    Text(
//                        modifier = Modifier
//                            .padding(vertical = 18.dp, horizontal = 8.dp),
//                        text = it.name
//                    )
//                }
//                Divider()
//            }
//        }
//
//        val loadState = trendingPagination.loadState.mediator
//        item {
//            if (loadState?.refresh == LoadState.Loading) {
//                Column(
//                    modifier = Modifier
//                        .fillParentMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    androidx.compose.material.Text(
//                        modifier = Modifier
//                            .padding(8.dp),
//                        text = "Refresh Loading"
//                    )
//
//                    androidx.compose.material.CircularProgressIndicator(color = MaterialTheme.colors.primary)
//                }
//            }
//
//            if (loadState?.append == LoadState.Loading) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    contentAlignment = Alignment.Center,
//                ) {
//                    androidx.compose.material.CircularProgressIndicator(color = MaterialTheme.colors.primary)
//                }
//            }
//
//            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
//                val isPaginatingError = (loadState.append is LoadState.Error) || trendingPagination.itemCount > 1
//                val error = if (loadState.append is LoadState.Error)
//                    (loadState.append as LoadState.Error).error
//                else
//                    (loadState.refresh as LoadState.Error).error
//
//                val modifier = if (isPaginatingError) {
//                    Modifier.padding(8.dp)
//                } else {
//                    Modifier.fillParentMaxSize()
//                }
//                Column(
//                    modifier = modifier,
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    if (!isPaginatingError) {
//                        Icon(
//                            modifier = Modifier
//                                .size(64.dp),
//                            imageVector = Icons.Rounded.Warning, contentDescription = null
//                        )
//                    }
//
//                    androidx.compose.material.Text(
//                        modifier = Modifier
//                            .padding(8.dp),
//                        text = error.message ?: error.toString(),
//                        textAlign = TextAlign.Center,
//                    )
//
//                    Button(
//                        onClick = {
//                            trendingPagination.refresh()
//                        },
//                        content = {
//                            androidx.compose.material.Text(text = "Refresh")
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = MaterialTheme.colors.primary,
//                            contentColor = Color.White,
//                        )
//                    )
//                }
//            }
//        }
//    }

}