package com.example.edistynyt_mobiiliohjelmointi.api

import com.example.edistynyt_mobiiliohjelmointi.model.AuthReq
import com.example.edistynyt_mobiiliohjelmointi.model.AuthRes
import com.example.edistynyt_mobiiliohjelmointi.model.UserId
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private val retrofitClient = Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val authService = retrofitClient.create(AuthApi::class.java)

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body req: AuthReq): AuthRes

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") bearerToken: String)

    @GET("auth/account")
    suspend fun getAccount(@Header("Authorization") bearerToken: String): Response<*>

    @GET("auth/account")
    suspend fun getUserId(@Header("Authorization") bearerToken: String): UserId
}