package com.example.themoviedb.presentation.account_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountInfo(
    viewModel: AccountInfoViewModel = hiltViewModel()
){
    val state by viewModel.accountState.collectAsState()

    Text(state.account.toString())
}
