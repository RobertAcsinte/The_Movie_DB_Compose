package com.example.themoviedb.presentation.login_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ApplicationContext private val context: Context
): ViewModel(){

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val sharedPreference =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()


    fun login(username: String, password: String){
        viewModelScope.launch {
            repository.login(username = username, password = password).collect() {
                when(it) {
                    is Resource.Success -> {
                        editor.putString("SESSION_ID", it.data?.sessionId).apply()
                        _loginState.value = _loginState.value.copy(sessionId = sharedPreference.getString("SESSION_ID", ""), error = null)
                    }
                    is Resource.Error -> {
                        _loginState.value = _loginState.value.copy(error = it.data?.statusMessage)
                    }
                    is Resource.Loading -> {
                        _loginState.value = _loginState.value.copy(isLoading = it.isLoading)
                    }
                }
            }
        }
    }
}