package com.example.themoviedb.presentation.account_screen

import com.example.themoviedb.data.remote.dto.UserResponse

data class AccountInfoState(
    val account: UserResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)