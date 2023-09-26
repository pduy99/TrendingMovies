package com.helios.trendingmovies.data.datsource

import com.helios.trendingmovies.data.local.MovieDao
import com.helios.trendingmovies.data.mapper.ModelMapper
import com.helios.trendingmovies.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by phpduy99 on 24/09/2023
 */
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val mapper: ModelMapper
) {
    fun getSavedTrendingMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().flowOn(Dispatchers.IO).map {
            it.map { roomMovie ->
                mapper.mapRoomMovieEntity(roomMovie)
            }
        }
    }

    fun getMovieById(id: Int): Flow<Movie?> {
        return movieDao.getMovieById(id).flowOn(Dispatchers.IO).map {
            it?.let {
                mapper.mapRoomMovieEntity(it)
            }
        }
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.update(mapper.mapToRoomMovie(movie))
    }

    suspend fun insertMovie(movie: Movie) {
        movieDao.insert(mapper.mapToRoomMovie(movie))
    }
}