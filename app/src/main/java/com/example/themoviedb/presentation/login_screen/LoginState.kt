package com.example.themoviedb.presentation.login_screen

import com.example.themoviedb.data.remote.dto.SessionId

data class LoginState(
    var sessionId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)