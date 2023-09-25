package com.helios.trendingmovies.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */

@kotlinx.serialization.Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1") val iso: String? = null,
    @SerialName("name") val name: String? = null,
)