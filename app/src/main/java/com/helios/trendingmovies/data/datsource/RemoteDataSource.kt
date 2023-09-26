package com.helios.trendingmovies.data.datsource

import android.util.Log
import com.helios.trendingmovies.data.mapper.ModelMapper
import com.helios.trendingmovies.data.remote.MovieApiService
import com.helios.trendingmovies.domain.model.Movie
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

/**
 * Created by phpduy99 on 24/09/2023
 */

private const val TAG = "RemoteDataSource"

class RemoteDataSource @Inject constructor(
    private val apiService: MovieApiService,
    private val mapper: ModelMapper
) : DataSource {

    private var currentPage = 1
    override fun getTrendingMovies(page: Int): Flow<Resource<List<Movie>>> {
        Log.d(TAG, "getTrendingMovies")
        return apiService.getTrendingMovies(page = currentPage).map {
            mapper.mapMoviePagingEntity(it)
        }.flowOn(Dispatchers.IO)
            .onCompletion {
                currentPage += 1
            }
    }

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> {
        return apiService.searchMovie(query).flowOn(Dispatchers.IO).map {
            mapper.mapMoviePagingEntity(it)
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie>> {
        return apiService.getMovieDetail(id).flowOn(Dispatchers.IO).map {
            mapper.mapMovieDetailEntity(it)
        }
    }
}