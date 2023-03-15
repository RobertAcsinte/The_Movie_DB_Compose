package com.example.themoviedb.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themoviedb.presentation.account_screen.AccountInfo
import com.example.themoviedb.presentation.trending_screen.TrendingScreen


@Composable
fun HomeScreen(
    viewModel:HomeViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit,
    navigate: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        ViewState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize()
            ){
                Box(modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))
                }
            }
        }
        ViewState.NotLoggedIn -> {
            LaunchedEffect(viewState) {
                if(viewState == ViewState.NotLoggedIn){
                    onNavigateToLoginScreen()
                }
            }
        }
        ViewState.LoggedIn -> {
            //AccountInfo()
            TrendingScreen() {
                navigate()
            }
        }
    }
}