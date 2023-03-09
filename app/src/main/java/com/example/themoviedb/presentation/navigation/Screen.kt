package com.example.themoviedb.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object AccountScreen: Screen("account_screen")
    object HomeScreen: Screen("home_screen")
}