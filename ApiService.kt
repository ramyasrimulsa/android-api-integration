package com.example.backendintegrationapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("posts/1")
    suspend fun getPost(): Response<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>
}
