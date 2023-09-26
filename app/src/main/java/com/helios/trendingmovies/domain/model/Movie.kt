package com.helios.trendingmovies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val adult: Boolean?,
    val backdropPath: String?,
    val genresName: List<String>,
    val homepage: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val tagline: String?,
    val originalLanguage: String?,
    val voteAverage: Double?,
    var detailLoaded: Boolean = false,
) {
    fun getFullImagePath(path: String): String {
        return "https://image.tmdb.org/t/p/w500$path"
    }
}
