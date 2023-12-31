package com.helios.trendingmovies.di

import android.content.Context
import com.helios.trendingmovies.data.datsource.LocalDataSource
import com.helios.trendingmovies.data.datsource.RemoteDataSource
import com.helios.trendingmovies.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by phpduy99 on 24/09/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        @ApplicationContext applicationContext: Context
    ) =
        MovieRepository(remoteDataSource, localDataSource, applicationContext)
}