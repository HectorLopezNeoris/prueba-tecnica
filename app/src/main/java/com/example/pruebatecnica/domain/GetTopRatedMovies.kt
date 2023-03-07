package com.example.pruebatecnica.domain

import com.example.pruebatecnica.data.MovieRepository
import com.example.pruebatecnica.data.database.entities.toDB
import com.example.pruebatecnica.domain.model.MovieItem
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(page: Int): List<MovieItem> {
        val movies = movieRepository.getTopRatedMoviesFromApi(page)

        return if(!movies.isNullOrEmpty()) {
            movieRepository.clearTopRatedMovies()
            movieRepository.insertTopRatedMovies(movies.map { it.toDB() })
            movies
        } else {
            movieRepository.getTopRatedMoviesFromDB()
        }
    }

}