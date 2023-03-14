package com.example.themoviedb.presentation.home_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel(){

    private val sessionId: Flow<String> = flow {
        val sharedPreferenceSessionId =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
        val sharedPreferenceSessionIdGuest =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
        val sessionId = sharedPreferenceSessionId.getString("SESSION_ID", "")
        val sessionIdGuest = sharedPreferenceSessionIdGuest.getString("SESSION_ID_GUEST", "")
        if (sessionId != null && sessionIdGuest == "") {
            emit(sessionId)
        }
        if(sessionIdGuest != null && sessionId == ""){
            emit(sessionIdGuest)
        }
    }

    val viewState = sessionId.map {
        if(it != ""){
            ViewState.LoggedIn
        }
        else{
            ViewState.NotLoggedIn
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = ViewState.Loading)

}