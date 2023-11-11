package com.example.handyman.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.handyman.data.repo.DefaultAuthRepository
import com.example.handyman.ui.viewmodels.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: DefaultAuthRepository
) : StateViewModel<AuthState>(AuthState()) {

    private val _errorEvent: MutableSharedFlow<String?> = MutableSharedFlow()
    val errorEvent = _errorEvent.asSharedFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password).fold(
                onSuccess = {
//                    setState {
//                        copy(isAuthorized = true)
//                    }
                },
                onFailure = {
                    _errorEvent.emit(it.message)
                }
            )
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(email, password).fold(
                onSuccess = {
//                    setState {
//                        copy(isAuthorized = true)
//                    }
                }, onFailure = {
                    _errorEvent.emit(it.message)
                }
            )
        }
    }

    fun isLogged(): Boolean {
        return authRepository.isLogged()
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}