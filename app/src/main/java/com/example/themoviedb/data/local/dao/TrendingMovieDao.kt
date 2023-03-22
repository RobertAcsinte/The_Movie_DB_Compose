package com.example.themoviedb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.data.local.entity.TrendingMovieEntity

@Dao
interface TrendingMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<TrendingMovieEntity>)

    @Query("SELECT * from trending_movies ORDER BY fetchedOrder ASC")
    fun getAll(): PagingSource<Int, TrendingMovieEntity>

    @Query("SELECT fetchedOrder from trending_movies ORDER BY fetchedOrder DESC LIMIT 1")
    fun getLastFetchedOrder(): Int?

    @Query("Delete From trending_movies")
    suspend fun clearAllMovies()
}