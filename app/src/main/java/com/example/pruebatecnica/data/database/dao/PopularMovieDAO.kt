package com.example.pruebatecnica.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnica.data.database.entities.PopularMovieEntity

@Dao
interface PopularMovieDAO {

    @Query("SELECT * FROM popular_movie_table ORDER BY title DESC")
    suspend fun getAllMovies(): List<PopularMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PopularMovieEntity>)

    @Query("DELETE FROM popular_movie_table")
    suspend fun deleteAllMovies()
}