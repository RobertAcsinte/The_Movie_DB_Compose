package com.example.themoviedb.presentation.login_screen

data class LoginState(
    var sessionId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)