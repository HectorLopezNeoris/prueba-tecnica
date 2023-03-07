package com.example.pruebatecnica.data.network

import com.example.pruebatecnica.data.network.response.ListMoviesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getPopularMovies(page: Int): ListMoviesModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getPopularMovies(page = page)
            response.body()
        }
    }

    suspend fun getTopRatedMovies(page: Int): ListMoviesModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getTopRatedMovies(page = page)
            response.body()
        }
    }

}