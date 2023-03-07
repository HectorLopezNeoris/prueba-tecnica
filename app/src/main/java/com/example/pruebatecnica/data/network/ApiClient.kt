package com.example.pruebatecnica.data.network

import com.example.pruebatecnica.core.Credentials
import com.example.pruebatecnica.data.network.response.ListMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET(value = "3/movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Credentials.API_KEY,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): Response<ListMoviesModel>

    @GET(value = "3/movie/top_rated?")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Credentials.API_KEY,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): Response<ListMoviesModel>

}