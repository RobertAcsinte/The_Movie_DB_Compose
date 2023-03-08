package com.example.themoviedb.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(

){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = { Text("Username") },
            value = "fdasf",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            label = { Text("Password") },
            value = "fdasf",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text("Login")
        }
    }
}