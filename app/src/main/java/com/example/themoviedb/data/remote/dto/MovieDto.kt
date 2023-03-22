package com.example.themoviedb.data.remote.dto

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class MovieDto(
    val adults: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    @SerializedName("name", alternate = arrayOf("title"))
    val name: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name", alternate = arrayOf("original_title"))
    val originalName: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("first_air_date", alternate = arrayOf("release_date"))
    val firstAirDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @ColumnInfo(name = "page")
    var page: Int,
)
