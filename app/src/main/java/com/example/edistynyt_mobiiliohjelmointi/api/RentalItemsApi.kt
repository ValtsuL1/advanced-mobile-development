package com.example.edistynyt_mobiiliohjelmointi.api

import android.util.Log
import com.example.edistynyt_mobiiliohjelmointi.model.CategoryResponse
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemsResponse
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = createClient()

val rentalItemsService = retrofit.create(RentalItemsApi::class.java)

interface RentalItemsApi {

    // Items by category
    @GET("category/{category_id}/items")
    suspend fun getItemsByCategory(@Path("category_id") id: Int): RentalItemsResponse

}