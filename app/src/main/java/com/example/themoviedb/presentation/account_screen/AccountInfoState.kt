package com.example.themoviedb.presentation.account_screen

import com.example.themoviedb.data.remote.dto.User

data class AccountInfoState(
    val account: User? = null,
    val isLoading: Boolean = false,
)