package com.example.edistynyt_mobiiliohjelmointi.api

import com.example.edistynyt_mobiiliohjelmointi.model.AddItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.EditItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.RentItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItem
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemResponse
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private val retrofit = createClient()

val rentalItemsService = retrofit.create(RentalItemsApi::class.java)

interface RentalItemsApi {

    // Items by category
    @GET("category/{category_id}/items")
    suspend fun getItemsByCategory(@Path("category_id") id: Int): RentalItemsResponse

    // Rent item
    @POST("rentalitem/{rental_item_id}/rent")
    suspend fun rentItem(
        @Path("rental_item_id") id: Int,
        @Body rentItemReq: RentItemReq
    ): RentalItemResponse

    // Edit item
    @PUT("rentalitem/{rental_item_id}")
    suspend fun editItem(
        @Path("rental_item_id") id: Int,
        @Body editItemReq: EditItemReq
    ): RentalItemResponse

    // Get item
    @GET("rentalitem/{rental_item_id}")
    suspend fun getItem(@Path("rental_item_id") id: Int): RentalItemResponse

    // Delete item
    @DELETE("rentalitem/{rental_item_id}")
    suspend fun deleteItem(@Path("rental_item_id") id: Int)

    // Add item
    @POST("category/{category_id}/items")
    suspend fun addItem(
        @Path("category_id") id: Int,
        @Body req: AddItemReq
    ): RentalItem
}