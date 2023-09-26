package com.helios.trendingmovies.data.remote.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */
@kotlinx.serialization.Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)