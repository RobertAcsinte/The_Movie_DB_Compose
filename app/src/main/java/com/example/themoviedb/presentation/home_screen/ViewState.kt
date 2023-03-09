package com.example.themoviedb.presentation.home_screen

sealed class ViewState {
    object Loading: ViewState() // hasLoggedIn = unknown
    object LoggedIn: ViewState() // hasLoggedIn = true
    object NotLoggedIn: ViewState() // hasLoggedIn = false
}