package com.example.pruebatecnica.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnica.data.database.entities.TopRatedMovieEntity

@Dao
interface TopRatedMovieDAO {

    @Query("SELECT * FROM top_rated_movie_table ORDER BY title DESC")
    suspend fun getAllMovies(): List<TopRatedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<TopRatedMovieEntity>)

    @Query("DELETE FROM top_rated_movie_table")
    suspend fun deleteAllMovies()

}