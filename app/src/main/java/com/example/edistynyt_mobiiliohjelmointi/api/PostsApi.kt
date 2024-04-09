package com.example.edistynyt_mobiiliohjelmointi.api

import com.example.edistynyt_mobiiliohjelmointi.model.Post
import retrofit2.http.GET

private val retrofit = createClient()

val postsService = retrofit.create(PostsApi::class.java)

interface PostsApi {
    @GET("posts")

    suspend fun getPosts(): List<Post>
}