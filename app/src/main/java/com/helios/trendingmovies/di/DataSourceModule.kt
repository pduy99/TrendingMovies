package com.helios.trendingmovies.di

import com.helios.trendingmovies.data.datsource.LocalDataSource
import com.helios.trendingmovies.data.datsource.RemoteDataSource
import com.helios.trendingmovies.network.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by phpduy99 on 24/09/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun providesRemoteDataSource(movieApiService: MovieApiService): RemoteDataSource =
        RemoteDataSource(movieApiService)

    @Singleton
    @Provides
    fun providesLocalDataSource(): LocalDataSource = LocalDataSource()
}