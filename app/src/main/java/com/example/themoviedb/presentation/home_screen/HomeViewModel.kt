package com.example.themoviedb.presentation.home_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel(){


    private val sessionId: Flow<String> = flow {
        val sharedPreference =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
        val sessionId = sharedPreference.getString("SESSION_ID", "")
        if (sessionId != null) {
            emit(sessionId)
        }
    }

    val viewState = sessionId.map {
        if(it != ""){
            ViewState.LoggedIn
        }
        else{ ViewState.NotLoggedIn
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = ViewState.Loading)

}