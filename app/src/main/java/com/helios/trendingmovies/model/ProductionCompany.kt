package com.helios.trendingmovies.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */
@kotlinx.serialization.Serializable
data class ProductionCompany(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val origin_country: String,
)