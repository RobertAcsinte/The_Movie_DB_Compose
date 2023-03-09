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
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    @ApplicationContext context: Context
){

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("pula") },
//                navController = navController
//            )
//        }
    ) { innerPadding ->
        var startScreen = Screen.LoginScreen.route
        val sharedPreference =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
        val sessionId = sharedPreference.getString("SESSION_ID", "")
        if(sessionId != ""){
            startScreen = Screen.AccountScreen.route
        }
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Screen.LoginScreen.route) {
                Login() {
                    navController.navigate(Screen.AccountScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(Screen.AccountScreen.route) {
                AccountInfo()
            }
        }
    }
}