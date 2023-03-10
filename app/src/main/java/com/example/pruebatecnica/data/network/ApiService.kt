package com.example.pruebatecnica.data.network

import com.example.pruebatecnica.data.network.response.ListMoviesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getPopularMovies(): ListMoviesModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getPopularMovies()
            response.body()
        }
    }

    suspend fun getTopRatedMovies(): ListMoviesModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getTopRatedMovies()
            response.body()
        }
    }

}