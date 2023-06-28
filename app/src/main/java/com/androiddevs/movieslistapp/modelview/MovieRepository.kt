package com.androiddevs.movieslistapp.modelview

import com.androiddevs.movieslistapp.ApiClient
import com.androiddevs.movieslistapp.model.Title
import retrofit2.Call

class MovieRepository {
    val apiService = ApiClient.apiService
    suspend fun getMovies(query: String): Call<Title> {
        return apiService.getMovies(query)
    }
}