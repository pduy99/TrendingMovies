package com.helios.trendingmovies.data.datsource

import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.model.MoviesCollection
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by phpduy99 on 24/09/2023
 */

interface DataSource {

    fun getTrendingMovies(): Flow<Resource<MoviesCollection>>

    fun searchMovie(query: String): Flow<Resource<MoviesCollection>>

    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>
}