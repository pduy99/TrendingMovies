package com.helios.trendingmovies.di

import android.content.Context
import androidx.room.Room
import com.helios.trendingmovies.data.local.MovieDao
import com.helios.trendingmovies.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(
        movieDatabase: MovieDatabase
    ): MovieDao = movieDatabase.movieDao()
}