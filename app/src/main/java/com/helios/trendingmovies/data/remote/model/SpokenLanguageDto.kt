package com.helios.trendingmovies.data.remote.model

import kotlinx.serialization.SerialName

/**
 * Created by phpduy99 on 24/09/2023
 */

@kotlinx.serialization.Serializable
data class SpokenLanguageDto(
    @SerialName("english_name") val englishName: String? = null,
    @SerialName("iso_639_1") val iso: String? = null,
    @SerialName("name") val name: String? = null
)