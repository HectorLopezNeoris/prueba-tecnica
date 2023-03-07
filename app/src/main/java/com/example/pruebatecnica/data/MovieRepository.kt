package com.example.pruebatecnica.data

import com.example.pruebatecnica.data.database.dao.PopularMovieDAO
import com.example.pruebatecnica.data.database.dao.TopRatedMovieDAO
import com.example.pruebatecnica.data.database.entities.PopularMovieEntity
import com.example.pruebatecnica.data.database.entities.TopRatedMovieEntity
import com.example.pruebatecnica.data.network.ApiService
import com.example.pruebatecnica.domain.model.MovieItem
import com.example.pruebatecnica.domain.model.toDomain
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val popularMovieDAO: PopularMovieDAO,
    private val topRatedMovieDAO: TopRatedMovieDAO
) {

    /**
     *  POPULAR MOVIES
     */

    suspend fun getPopularMoviesFromApi(page: Int): List<MovieItem> {
        val response = apiService.getPopularMovies(page)
        return response?.results?.map { it.toDomain() } as MutableList<MovieItem>
    }

    suspend fun getPopularMoviesFromDB(): List<MovieItem> {
        return popularMovieDAO.getAllMovies().map { it.toDomain() }
    }

    suspend fun insertPopularMovies(movies: List<PopularMovieEntity>) {
        popularMovieDAO.insertAll(movies)
    }

    suspend fun clearPopularMovies() {
        popularMovieDAO.deleteAllMovies()
    }

    /**
     *  TOP RATED MOVIES
     */

    suspend fun getTopRatedMoviesFromApi(page: Int): List<MovieItem> {
        val response = apiService.getTopRatedMovies(page)
        return response?.results?.map { it.toDomain() } as MutableList<MovieItem>
    }

    suspend fun getTopRatedMoviesFromDB(): List<MovieItem> {
        return topRatedMovieDAO.getAllMovies().map { it.toDomain() }
    }

    suspend fun insertTopRatedMovies(movies: List<TopRatedMovieEntity>) {
        topRatedMovieDAO.insertAll(movies)
    }

    suspend fun clearTopRatedMovies() {
        topRatedMovieDAO.deleteAllMovies()
    }

}