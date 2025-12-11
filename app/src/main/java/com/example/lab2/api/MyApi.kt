package com.example.lab2.api

import com.example.lab2.dto.CategoryDTO
import com.example.lab2.dto.TaskDTO
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET("tasks")
    suspend fun getTasks(): Response<List<TaskDTO>>

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDTO>>

}