package com.example.themoviedb.di

import android.app.Application
import androidx.room.Room
import com.example.themoviedb.data.local.TrendingDatabase
import com.example.themoviedb.data.local.dao.RemoteKeysDao
import com.example.themoviedb.data.local.dao.TrendingMovieDao
import com.example.themoviedb.data.local.entity.Converters
import com.example.themoviedb.data.remote.MovieApi
import com.example.themoviedb.util.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AndroidModule {
    @Provides
    @Singleton
    fun provideStockApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): TrendingDatabase {
        return Room.databaseBuilder(
            app,
            TrendingDatabase::class.java,
            "trendingDb.db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(trendingDatabase: TrendingDatabase): TrendingMovieDao = trendingDatabase.trendingMovieDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(trendingDatabase: TrendingDatabase): RemoteKeysDao = trendingDatabase.remoteKeysDao()
}

