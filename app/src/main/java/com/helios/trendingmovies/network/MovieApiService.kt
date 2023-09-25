package com.helios.trendingmovies.network

import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.model.MoviesCollection
import com.helios.trendingmovies.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by phpduy99 on 24/09/2023
 */
interface MovieApiService {
    @GET("trending/movie/{time_window}")
    fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1
    ): Flow<Resource<MoviesCollection>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Flow<Resource<MovieDetail>>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String): Flow<Resource<MoviesCollection>>
}