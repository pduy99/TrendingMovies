package com.helios.trendingmovies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by phpduy99 on 24/09/2023
 */
@Serializable
data class Movie(
    @SerialName("adult") var adult: Boolean? = null,
    @SerialName("backdrop_path") var backdropPath: String? = null,
    @SerialName("id") var id: Int? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("original_language") var originalLanguage: String? = null,
    @SerialName("original_title") var originalTitle: String? = null,
    @SerialName("overview") var overview: String? = null,
    @SerialName("poster_path") var posterPath: String? = null,
    @SerialName("media_type") var mediaType: String? = null,
    @SerialName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerialName("popularity") var popularity: Double? = null,
    @SerialName("release_date") var releaseDate: String? = null,
    @SerialName("video") var video: Boolean? = null,
    @SerialName("vote_average") var voteAverage: Double? = null,
    @SerialName("vote_count") var voteCount: Int? = null
) {
    fun getFullImagePath(path: String): String {
        return "https://image.tmdb.org/t/p/w500$path"
    }
}