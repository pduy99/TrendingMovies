package com.helios.trendingmovies.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */
@kotlinx.serialization.Serializable
data class MovieDetail(
    @SerialName("adult") var adult: Boolean? = null,
    @SerialName("backdrop_path") var backdropPath: String? = null,
    @SerialName("genres") var genres: ArrayList<Genre> = arrayListOf(),
    @SerialName("homepage") var homepage: String? = null,
    @SerialName("id") var id: Int? = null,
    @SerialName("original_language") var originalLanguage: String? = null,
    @SerialName("original_title") var originalTitle: String? = null,
    @SerialName("overview") var overview: String? = null,
    @SerialName("poster_path") var posterPath: String? = null,
    @SerialName("release_date") var releaseDate: String? = null,
    @SerialName("runtime") var runtime: Int? = null,
    @SerialName("spoken_languages") var spokenLanguages: ArrayList<SpokenLanguage>? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("tagline") var tagline: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("video") var video: Boolean? = null,
    @SerialName("vote_average") var voteAverage: Double? = null,
    @SerialName("vote_count") var voteCount: Int? = null
) {
    fun getFullImagePath(path: String): String {
        return "https://image.tmdb.org/t/p/w500$path"
    }
}