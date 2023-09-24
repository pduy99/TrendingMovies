package com.helios.trendingmovies.data.datsource

import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.model.MoviesCollection
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by phpduy99 on 24/09/2023
 */
class LocalDataSource : DataSource {
    override fun getTrendingMovies(): Flow<Resource<MoviesCollection>> {
        TODO("Not yet implemented")
    }

    override fun searchMovie(query: String): Flow<Resource<MoviesCollection>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> {
        TODO("Not yet implemented")
    }
}