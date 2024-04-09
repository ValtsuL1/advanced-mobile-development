package com.example.edistynyt_mobiiliohjelmointi.api

import com.example.edistynyt_mobiiliohjelmointi.model.AddCategoryReq
import com.example.edistynyt_mobiiliohjelmointi.model.CategoriesResponse
import com.example.edistynyt_mobiiliohjelmointi.model.CategoryItem
import com.example.edistynyt_mobiiliohjelmointi.model.CategoryResponse
import com.example.edistynyt_mobiiliohjelmointi.model.EditCategoryReq
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private val retrofit = createClient()

val categoriesService = retrofit.create(CategoriesApi::class.java)

interface CategoriesApi {
    @GET("category/")
    suspend fun getCategories(): CategoriesResponse

    @POST("category/")
    suspend fun createCategory(@Body req: AddCategoryReq): CategoryItem

    @GET("category/{id}")
    suspend fun getCategory(@Path("id") id: Int): CategoryResponse

    @DELETE("category/{id}")
    suspend fun removeCategory(@Path("id") id: Int)

    @PUT("category/{id}")
    suspend fun editCategory(
        @Path("id") id: Int,
        @Body editCategoryReq: EditCategoryReq
    ): CategoryResponse
}