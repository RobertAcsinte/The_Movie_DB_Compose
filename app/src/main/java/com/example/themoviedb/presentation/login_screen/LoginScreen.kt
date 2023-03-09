package com.example.themoviedb.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themoviedb.presentation.account_screen.AccountInfoViewModel
import com.example.themoviedb.presentation.login_screen.LoginState
import com.example.themoviedb.presentation.login_screen.LoginViewModel
import com.example.themoviedb.ui.theme.ErrorColorDark


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginButtonClicked: () -> Unit
){
    var username by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    val state by viewModel.loginState.collectAsState()
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 128.dp),
        text = "The Movie Db",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 54.sp
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text("Username") },
            value = username,
            onValueChange = { username = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            label = { Text("Password") },
            value = password,
            onValueChange = { password = it }
        )
        Spacer(modifier = Modifier.size(16.dp))
        if(state.isLoading){
            CircularProgressIndicator()
        }
        else{
            Button(
                onClick = {
                    viewModel.login(username, password)
//                    onLoginButtonClicked()
                }
            ) {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        state.sessionId?.let {
            LaunchedEffect(Unit){
                onLoginButtonClicked()
            }
        }
        state.error?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                color = ErrorColorDark,
                fontWeight = FontWeight.Bold
            )
        }
    }
}