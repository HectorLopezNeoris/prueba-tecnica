package com.example.pruebatecnica.domain.model

import com.example.pruebatecnica.core.Credentials
import com.example.pruebatecnica.data.database.entities.PopularMovieEntity
import com.example.pruebatecnica.data.database.entities.TopRatedMovieEntity
import com.example.pruebatecnica.data.network.response.Movies

data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String
)

fun Movies.toDomain(): MovieItem {
    val poster = "${Credentials.PATH_IMG}${this.posterPath}"
    return MovieItem(
        id = id,
        title = title,
        posterPath = poster,
        overview = overview,
        releaseDate = releaseDate
    )
}

fun PopularMovieEntity.toDomain(): MovieItem {
    val poster = "${Credentials.PATH_IMG}${this.posterPath}"
    return MovieItem(
        id = id,
        title = title,
        posterPath = poster,
        overview = overview,
        releaseDate = releaseDate
    )
}

fun TopRatedMovieEntity.toDomain(): MovieItem {
    val poster = "${Credentials.PATH_IMG}${this.posterPath}"
    return MovieItem(
        id = id,
        title = title,
        posterPath = poster,
        overview = overview,
        releaseDate = releaseDate
    )
}