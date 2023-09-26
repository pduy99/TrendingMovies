package com.helios.trendingmovies.data.datsource

import com.helios.trendingmovies.domain.model.Movie
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by phpduy99 on 24/09/2023
 */

interface DataSource {

    fun getTrendingMovies(page: Int): Flow<Resource<List<Movie>>>

    fun searchMovie(query: String): Flow<Resource<List<Movie>>>

    fun getMovieDetail(id: Int): Flow<Resource<Movie>>
}