package com.example.themoviedb.presentation.account_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ApplicationContext private val context: Context
): ViewModel(){

    private var _accountState = MutableStateFlow(AccountInfoState())
    var accountState = _accountState.asStateFlow()

    private val sharedPreference =  context.getSharedPreferences("PREFERENCE_SESSION", Context.MODE_PRIVATE)
    private var editor = sharedPreference.edit()

    init {
        getAccountInfo()
    }


    private fun getAccountInfo(){
        val sessionId = sharedPreference.getString("SESSION_ID", "")
        if(sessionId == "") {
            _accountState.value = _accountState.value.copy(error = "You are logged in as a guest, you cannot see details about your account")
        }
        else{
            viewModelScope.launch {
                if (sessionId != null) {
                    repository
                        .getUser(sessionId)
                        .collect() {
                            when(it) {
                                is Resource.Success -> {
                                    it.data?.let {
                                        _accountState.value = _accountState.value.copy(account = it)
                                    }
                                }
                                is Resource.Error -> {
                                    _accountState.value = _accountState.value.copy(error = "You are logged in as a guest, you cannot see details about your account")
                                }
                                is Resource.Loading -> {
                                    _accountState.value = _accountState.value.copy(isLoading = it.isLoading)
                                }
                            }
                        }
                }
            }
        }
    }
}