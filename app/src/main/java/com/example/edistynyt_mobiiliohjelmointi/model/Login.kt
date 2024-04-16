package com.example.edistynyt_mobiiliohjelmointi.model

import com.google.gson.annotations.SerializedName

data class LoginState(
    val username: String = "",
    val password: String = "",
    val loginOk: Boolean = false,
    val loading: Boolean = false,
    val err: String? = null
)

data class RegistrationState(
    val username: String = "",
    val password: String = "",
    val registerOk: Boolean = false,
    val loading: Boolean = false,
    val err: String? = null
)

data class AuthReq(
    val username: String = "",
    val password: String = ""
)

data class AuthRes(
    @SerializedName("access_token")
    val accessToken: String = ""
)

data class LogoutState(
    val logoutOk: Boolean = false,
    val loading: Boolean = false,
    val err: String? = null
)

data class UserId(
    @SerializedName("auth_user_id")
    val id: Int = 0
)