package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.AccountDatabase
import com.example.edistynyt_mobiiliohjelmointi.AccountEntity
import com.example.edistynyt_mobiiliohjelmointi.DbProvider
import com.example.edistynyt_mobiiliohjelmointi.MainActivity
import com.example.edistynyt_mobiiliohjelmointi.api.authService
import com.example.edistynyt_mobiiliohjelmointi.model.AuthReq
import com.example.edistynyt_mobiiliohjelmointi.model.LoginState
import com.example.edistynyt_mobiiliohjelmointi.model.LogoutState
import kotlinx.coroutines.launch

class LoginViewModel(private val db: AccountDatabase = DbProvider.db): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    init {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()
                if(accessToken != null) {
                    val res = authService.getAccount("Bearer $accessToken")
                    if(res.code() == 200){
                        setLogin(true)
                        setUserId()
                    }
                }
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(err = e.toString())
            } finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }
    }

    private fun setUserId() {
        viewModelScope.launch {
            try {
                val accessToken = db.accountDao().getToken()
                MainActivity.userId = authService.getUserId("Bearer $accessToken").id
            } catch (e: Exception) {
                Log.d("e", e.toString())
            }
        }
    }

    fun setUsername(username: String) {
        _loginState.value = _loginState.value.copy(username = username)
    }

    fun setPassword(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }

    fun setLogin(ok: Boolean) {
        _loginState.value = _loginState.value.copy(loginOk = ok)
        MainActivity.isLoggedIn = true
    }

    fun setLogout(ok: Boolean) {
        _logoutState.value = _logoutState.value.copy(logoutOk = ok)
        MainActivity.isLoggedIn = false
    }

    fun login() {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                val res = authService.login(
                    AuthReq(
                        username = loginState.value.username,
                        password = loginState.value.password
                    )
                )
                db.accountDao().addToken(
                    AccountEntity(accessToken = res.accessToken)
                )
                setLogin(true)
                setUserId()
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(err = e.toString())
            } finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }
    }

    fun register(goToLogin: () -> Unit) {
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                authService.register(
                    AuthReq(
                        username = loginState.value.username,
                        password = loginState.value.password
                    )
                )
                goToLogin()
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(err = e.toString())
            } finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _logoutState.value = _logoutState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()
                accessToken?.let {
                    authService.logout("Bearer $it")
                    db.accountDao().removeTokens()
                }
                setLogout(true)
            } catch (e: Exception) {
                _logoutState.value = _logoutState.value.copy(err = e.toString())
            } finally {
                _logoutState.value = _logoutState.value.copy(loading = false)
            }
        }
    }
}
