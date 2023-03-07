package com.example.pruebatecnica.data.network.response

import com.google.gson.annotations.SerializedName

data class ListMoviesModel(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: MutableList<Movies>,
    @SerializedName("total_pages") val totalPages: Int
)

data class Movies(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
)
