package com.helios.trendingmovies.data.remote.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */
@kotlinx.serialization.Serializable
data class MoviesPagingDto(
    @SerialName("page") val page: Int,
    @SerialName("results") var movieDtos: List<MovieDto>,
    @SerialName("total_pages") var totalPages: Int,
    @SerialName("total_results") var totalResults: Int
)