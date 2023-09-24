package com.helios.trendingmovies.data.repository

import com.helios.trendingmovies.data.datsource.RemoteDataSource
import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.model.MoviesCollection
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by phpduy99 on 24/09/2023
 */

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getTrendingMovies(): Flow<Resource<MoviesCollection>> {
        return remoteDataSource.getTrendingMovies()
    }

    fun searchMovie(query: String): Flow<Resource<MoviesCollection>> {
        return remoteDataSource.searchMovie(query)
    }

    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> {
        return remoteDataSource.getMovieDetail(id)
    }
}