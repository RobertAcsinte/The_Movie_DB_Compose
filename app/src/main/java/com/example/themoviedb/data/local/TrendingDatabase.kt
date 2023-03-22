package com.example.themoviedb.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedb.data.local.dao.RemoteKeysDao
import com.example.themoviedb.data.local.dao.TrendingMovieDao
import com.example.themoviedb.data.local.entity.Converters
import com.example.themoviedb.data.local.entity.RemoteKeysEntity
import com.example.themoviedb.data.local.entity.TrendingMovieEntity

@Database(
    entities = [TrendingMovieEntity::class, RemoteKeysEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class TrendingDatabase: RoomDatabase() {
    abstract fun trendingMovieDao(): TrendingMovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}