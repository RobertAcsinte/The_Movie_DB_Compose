package com.example.themoviedb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trending_movies")
data class TrendingMovieEntity(
    @PrimaryKey
    val id: Int,
    val adults: Boolean,
    val backdropPath: String?,
    val name: String,
    val originalLanguage: String,
    val originalName: String?,
    val overview: String,
    val posterPath: String?,
    val mediaType: String,
    val genreIds: List<Int>,
    val firstAirDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val originCountry: List<String>?,
    var page: Int,
    var fetchedOrder: Int? = null,
)


