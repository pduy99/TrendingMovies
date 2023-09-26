package com.helios.trendingmovies.di

import com.helios.trendingmovies.data.mapper.ModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesModelMapper(): ModelMapper = ModelMapper()
}