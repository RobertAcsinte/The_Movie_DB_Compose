package com.example.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName


data class Trending(
    val page: String,
    val results: List<Movie>
)

data class Movie(
    val adults: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    @SerializedName("name", alternate = arrayOf("title"))
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>,
)