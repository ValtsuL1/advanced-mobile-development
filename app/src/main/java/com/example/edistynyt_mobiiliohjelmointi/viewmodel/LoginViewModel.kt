package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.model.LoginReqModel
import com.example.edistynyt_mobiiliohjelmointi.model.LoginResModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = mutableStateOf(LoginReqModel())
    val loginState: State<LoginReqModel> = _loginState

    fun setUsername(newUsername: String) {
        _loginState.value = loginState.value.copy(username = newUsername)
    }

    fun setPassword(newPassword: String) {
        _loginState.value = loginState.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(loading = true)
            val user = LoginResModel()
            _loginState.value = loginState.value.copy(loading = false)
        }

    }
}
