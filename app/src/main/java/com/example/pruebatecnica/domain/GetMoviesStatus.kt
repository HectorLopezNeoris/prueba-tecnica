package com.example.pruebatecnica.domain.getPopularMovies

import com.example.pruebatecnica.domain.model.MovieItem

sealed class GetMoviesStatus {

    object Loading: GetMoviesStatus()
    object Done: GetMoviesStatus()
    object EmptyList: GetMoviesStatus()
    data class Failure(val error: GetMoviesError): GetMoviesStatus()
    data class Success(val listMovies: List<MovieItem>): GetMoviesStatus()

}

enum class GetMoviesError {
    DEFAULT_ERROR,
    NETWORK_ERROR,
    PARSING_ERROR,
}

