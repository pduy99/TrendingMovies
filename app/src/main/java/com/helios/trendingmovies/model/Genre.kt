package com.helios.trendingmovies.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */
@kotlinx.serialization.Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)