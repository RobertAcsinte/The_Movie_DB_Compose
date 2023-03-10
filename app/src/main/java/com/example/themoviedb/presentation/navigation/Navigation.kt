package com.example.themoviedb.presentation.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviedb.presentation.Login
import com.example.themoviedb.presentation.account_screen.AccountInfo
import com.example.themoviedb.presentation.home_screen.HomeScreen
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("pula") },
//                navController = navController
//            )
//        }
    ) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Screen.LoginScreen.route) {
                Login() {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
//                    navController.navigateUp()
                }
            }
            composable(Screen.HomeScreen.route) {
                HomeScreen() {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
//                HomeScreen() {
//                    navController.navigate(Screen.LoginScreen.route)
//                }
            }
            composable(Screen.AccountScreen.route) {
                AccountInfo()
            }
        }
    }
}