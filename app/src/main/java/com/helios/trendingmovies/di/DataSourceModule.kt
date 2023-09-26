package com.helios.trendingmovies.di

import com.helios.trendingmovies.data.datsource.LocalDataSource
import com.helios.trendingmovies.data.datsource.RemoteDataSource
import com.helios.trendingmovies.data.local.MovieDao
import com.helios.trendingmovies.data.mapper.ModelMapper
import com.helios.trendingmovies.data.remote.MovieApiService
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
    fun providesRemoteDataSource(
        movieApiService: MovieApiService,
        mapper: ModelMapper
    ): RemoteDataSource =
        RemoteDataSource(movieApiService, mapper)

    @Singleton
    @Provides
    fun providesLocalDataSource(movieDao: MovieDao, mapper: ModelMapper): LocalDataSource =
        LocalDataSource(movieDao, mapper)
}