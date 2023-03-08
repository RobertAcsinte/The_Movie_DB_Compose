package com.example.themoviedb.presentation.account_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.domain.repository.MovieRepository
import com.example.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel(){

    private var _accountState = MutableStateFlow(AccountInfoState())
    var accountState = _accountState.asStateFlow()

    init {
        getAccountInfo()
    }


    private fun getAccountInfo(){
        viewModelScope.launch {
            repository
                .getUser()
                .collect() {
                    when(it) {
                        is Resource.Success -> {
                            it.data?.let {
                                _accountState.value = accountState.value.copy(account = it)
                            }
                        }
                        is Resource.Error -> {
                            println("sloboz")
                            //TO DO
                        }
                        is Resource.Loading -> {
                            _accountState.value = _accountState.value.copy(isLoading = it.isLoading)
                        }
                    }
                }

        }
    }
}