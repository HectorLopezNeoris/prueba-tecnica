package com.example.pruebatecnica.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebatecnica.domain.model.MovieItem

@Entity(tableName = "top_rated_movie_table")
class TopRatedMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String
)

fun MovieItem.toDB() =
    TopRatedMovieEntity(
        title = title,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate
    )