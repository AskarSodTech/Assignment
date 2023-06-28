package com.androiddevs.movieslistapp

import com.androiddevs.movieslistapp.model.Title
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("title/find")
    suspend fun searchTitles(@Query("q") query: String): Title

    @GET("title/find")
    fun getMovies(@Query("q") query: String): Call<Title>
}

object ApiClient {
    private const val BASE_URL = "https://imdb8.p.rapidapi.com/"
    private const val API_KEY = "arGiw6O4PtmshRnu6VGgrcJiO3DJp1cuMVojsnEABg5WRSaZVu"
    private const val HOST = "imdb8.p.rapidapi.com"

    private val retrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", HOST)
                    .build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
