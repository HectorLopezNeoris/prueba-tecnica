package com.example.pruebatecnica.data.network

import com.example.pruebatecnica.core.Credentials
import com.example.pruebatecnica.data.network.response.ListMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET(value = "3/movie/popular?")
    suspend fun getPopularMovies(): Response<ListMoviesModel>

    @GET(value = "3/movie/top_rated?")
    suspend fun getTopRatedMovies(): Response<ListMoviesModel>

}