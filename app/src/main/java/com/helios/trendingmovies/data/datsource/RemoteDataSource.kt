package com.helios.trendingmovies.data.datsource

import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.model.MoviesCollection
import com.helios.trendingmovies.network.MovieApiService
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by phpduy99 on 24/09/2023
 */
class RemoteDataSource @Inject constructor(
    private val apiService: MovieApiService
) : DataSource {

    override fun getTrendingMovies(): Flow<Resource<MoviesCollection>> {
        return apiService.getTrendingMovies().flowOn(Dispatchers.IO)
    }

    override fun searchMovie(query: String): Flow<Resource<MoviesCollection>> {
        return apiService.searchMovie(query).flowOn(Dispatchers.IO)
    }

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> {
        return apiService.getMovieDetail(id).flowOn(Dispatchers.IO)
    }
}