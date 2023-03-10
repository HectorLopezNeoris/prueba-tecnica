package com.example.pruebatecnica.domain

import com.example.pruebatecnica.data.MovieRepository
import com.example.pruebatecnica.data.database.entities.toDatabase
import com.example.pruebatecnica.domain.model.MovieItem
import javax.inject.Inject

class GetPopularMovies @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<MovieItem> {
        val movies = movieRepository.getPopularMoviesFromApi()

        return if(!movies.isNullOrEmpty()) {
            movieRepository.clearPopularMovies()
            movieRepository.insertPopularMovies(movies.map { it.toDatabase() })
            movies
        } else {
            movieRepository.getPopularMoviesFromDB()
        }
    }

}