package com.helios.trendingmovies.data.mapper

import com.helios.trendingmovies.data.local.model.RoomMovie
import com.helios.trendingmovies.data.remote.model.MovieDetailDto
import com.helios.trendingmovies.data.remote.model.MovieDto
import com.helios.trendingmovies.data.remote.model.MoviesPagingDto
import com.helios.trendingmovies.domain.model.Movie
import com.helios.trendingmovies.utils.Resource

class ModelMapper {

    fun mapMovieEntity(resource: Resource<MovieDto>): Resource<Movie> {
        return with(resource) {
            when (this) {
                is Resource.Loading -> {
                    Resource.Loading
                }

                is Resource.Error -> {
                    Resource.Error(this.throwable)
                }

                is Resource.Success -> {
                    Resource.Success(
                        Movie(
                            adult = this.data.adult,
                            backdropPath = this.data.backdropPath,
                            genresName = emptyList(),
                            homepage = null,
                            id = this.data.id,
                            overview = this.data.overview,
                            posterPath = this.data.posterPath,
                            releaseDate = this.data.releaseDate,
                            runtime = null,
                            tagline = null,
                            title = this.data.title,
                            originalLanguage = this.data.originalLanguage,
                            voteAverage = this.data.voteAverage,
                            detailLoaded = false
                        )
                    )
                }
            }

        }
    }

    fun mapMovieDetailEntity(resource: Resource<MovieDetailDto>): Resource<Movie> {
        return with(resource) {
            when (this) {
                is Resource.Loading -> {
                    Resource.Loading
                }

                is Resource.Error -> {
                    Resource.Error(this.throwable)
                }

                is Resource.Success -> {
                    Resource.Success(
                        Movie(
                            adult = this.data.adult,
                            backdropPath = this.data.backdropPath,
                            genresName = this.data.genreDtos.map { it.name },
                            homepage = this.data.homepage,
                            id = this.data.id,
                            overview = this.data.overview,
                            posterPath = this.data.posterPath,
                            releaseDate = this.data.releaseDate,
                            runtime = this.data.runtime,
                            tagline = this.data.tagline,
                            originalLanguage = this.data.originalLanguage,
                            title = this.data.title,
                            voteAverage = this.data.voteAverage,
                            detailLoaded = true
                        )
                    )
                }
            }

        }
    }

    fun mapMoviePagingEntity(resource: Resource<MoviesPagingDto>): Resource<List<Movie>> {
        return with(resource) {
            when (this) {
                is Resource.Loading -> {
                    Resource.Loading
                }

                is Resource.Error -> {
                    Resource.Error(this.throwable)
                }

                is Resource.Success -> {
                    val listMoviesDto = this.data.movieDtos

                    Resource.Success(listMoviesDto.map {
                        Movie(
                            adult = it.adult,
                            backdropPath = it.backdropPath,
                            genresName = emptyList(),
                            homepage = null,
                            id = it.id,
                            overview = it.overview,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            runtime = null,
                            tagline = null,
                            title = it.title,
                            voteAverage = it.voteAverage,
                            originalLanguage = it.originalLanguage,
                            detailLoaded = false
                        )
                    })
                }
            }
        }
    }

    fun mapRoomMovieEntity(roomMovie: RoomMovie): Movie {
        return with(roomMovie) {
            Movie(
                adult = this.adult,
                backdropPath = this.backdropPath,
                genresName = this.genresName,
                homepage = this.homepage,
                id = this.id,
                overview = this.overview,
                posterPath = this.posterPath,
                releaseDate = this.releaseDate,
                runtime = this.runtime,
                tagline = this.tagline,
                title = this.title,
                voteAverage = this.voteAverage,
                originalLanguage = this.originalLanguage,
                detailLoaded = this.detailLoaded
            )
        }
    }

    fun mapToRoomMovie(movie: Movie): RoomMovie {
        return with(movie) {
            RoomMovie(
                adult = this.adult,
                backdropPath = this.backdropPath,
                genresName = this.genresName,
                homepage = this.homepage,
                id = this.id,
                overview = this.overview,
                posterPath = this.posterPath,
                releaseDate = this.releaseDate,
                runtime = this.runtime,
                tagline = this.tagline,
                title = this.title,
                originalLanguage = this.originalLanguage,
                voteAverage = this.voteAverage,
                detailLoaded = this.detailLoaded
            )
        }
    }
}