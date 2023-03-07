package com.example.pruebatecnica.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnica.data.database.dao.PopularMovieDAO
import com.example.pruebatecnica.data.database.dao.TopRatedMovieDAO
import com.example.pruebatecnica.data.database.entities.PopularMovieEntity
import com.example.pruebatecnica.data.database.entities.TopRatedMovieEntity

@Database(entities = [PopularMovieEntity::class, TopRatedMovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun getPopularMovieDao(): PopularMovieDAO
    abstract fun getTopRatedMovieDao(): TopRatedMovieDAO

}