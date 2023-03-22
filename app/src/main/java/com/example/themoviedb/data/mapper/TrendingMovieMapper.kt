package com.example.themoviedb.data.mapper

import com.example.themoviedb.data.local.entity.TrendingMovieEntity
import com.example.themoviedb.data.remote.dto.MovieDto

fun MovieDto.toTrendingMovieEntity(): TrendingMovieEntity {
    return TrendingMovieEntity(
    id = id,
    adults = adults,
    backdropPath = backdropPath,
    name = name,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    genreIds = genreIds,
    firstAirDate = firstAirDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    originCountry = originCountry,
    page = page
    )
}