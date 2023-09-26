package com.helios.trendingmovies.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.helios.trendingmovies.data.datsource.LocalDataSource
import com.helios.trendingmovies.data.datsource.RemoteDataSource
import com.helios.trendingmovies.domain.model.Movie
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by phpduy99 on 24/09/2023
 */
private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationContext: Context
) {

    private val trendingMovies: MutableList<Movie> = mutableListOf()

    fun getTrendingMovies(needFetchRemote: Boolean = false): Flow<Resource<List<Movie>>> {
        return if (needFetchRemote) {
            fetchRemoteAndCacheTrendingMovies()
        } else {
            flow {
                emit(Resource.Loading)
                val localMovies = localDataSource.getSavedTrendingMovies().firstOrNull()
                if (!localMovies.isNullOrEmpty()) {
                    emit(Resource.Success(localMovies))
                } else {
                    fetchRemoteAndCacheTrendingMovies().onEach {
                        emit(it)
                    }.collect()
                }
            }
        }
    }

    fun searchMovie(query: String): Flow<Resource<List<Movie>>> {
        return remoteDataSource.searchMovie(query)
    }

    fun getMovieDetail(id: Int): Flow<Resource<Movie>> {
        return flow {
            val localMovie = localDataSource.getMovieById(id).firstOrNull()
            if (localMovie != null && localMovie.detailLoaded) {
                emit(Resource.Success(localMovie))
            } else {
                remoteDataSource.getMovieDetail(id).onEach { resource ->
                    emit(resource)
                }.collect()
            }
        }
    }

    private suspend fun saveMovieToLocal(movie: Movie) {
        localDataSource.insertMovie(movie)
    }

    private fun fetchRemoteAndCacheTrendingMovies(): Flow<Resource<List<Movie>>> {
        if (!isDeviceNetworkConnected()) {
            return flow { emit(Resource.Error(Throwable("Device is offline"))) }
        } else {
            Log.d(TAG, "fetchRemoteAndCacheTrendingMovies")
            return remoteDataSource.getTrendingMovies(calculateLoadingPage(trendingMovies.size))
                .map { resource ->
                    if (resource is Resource.Success) {
                        resource.data.forEach { movie ->
                            if (!trendingMovies.contains(movie)) {
                                trendingMovies.add(movie)
                            }
                            saveMovieToLocal(movie)
                        }
                        return@map Resource.Success(trendingMovies)
                    } else {
                        resource
                    }
                }
        }
    }

    private fun calculateLoadingPage(totalMovies: Int): Int {
        return (totalMovies / 20) + 1
    }

    private fun isDeviceNetworkConnected(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return activeNetwork != null && connectivityManager.getNetworkCapabilities(activeNetwork)
            ?.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) == true
    }
}