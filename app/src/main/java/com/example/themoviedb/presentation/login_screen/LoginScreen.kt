package com.example.themoviedb.presentation


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themoviedb.R
import com.example.themoviedb.presentation.login_screen.LoginViewModel
import com.example.themoviedb.ui.theme.ErrorColorDark
import com.example.themoviedb.ui.theme.OrangeDark
import com.example.themoviedb.ui.theme.OrangeLight
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateHome: () -> Unit
){
    var username by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = false) }

    val state by viewModel.loginState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    Column(
        modifier = Modifier
            .padding(top = 64.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 54.sp
        )
        Spacer(modifier = Modifier.size(48.dp))
        OutlinedTextField(
            modifier = Modifier
                .onFocusEvent {
                    if(it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            label = { Text(stringResource(id = R.string.username)) },
            value = username,
            onValueChange = {username = it})
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .onFocusEvent {
                    if(it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            },
            label = { Text(stringResource(id = R.string.password)) },
            value = password,
            onValueChange = {password = it})

        Spacer(modifier = Modifier.size(16.dp))
        if(state.isLoading){
            CircularProgressIndicator()
        }
        else{
            Button(
                onClick = { viewModel.login(username, password)}) {
                Text(stringResource(id = R.string.login))
            }
            Spacer(modifier = Modifier.size(16.dp))
            ClickableText(
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .padding(bottom = 16.dp),
                text = AnnotatedString(
                    stringResource(id = R.string.guest)
                ),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                onClick = { viewModel.loginGuest()}
            )
        }
        Spacer(modifier = Modifier.size(16.dp))

        state.sessionId?.let {
            LaunchedEffect(state){
                navigateHome()
            }
        }
        state.error?.let {
            Text(
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .padding(bottom = 16.dp),
                text = it,
                textAlign = TextAlign.Center,
                color = ErrorColorDark,
                fontWeight = FontWeight.Bold
            )
        }
    }
}