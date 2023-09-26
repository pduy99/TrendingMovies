package com.helios.trendingmovies.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(tableName = "movies")
data class RoomMovie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var overview: String? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    var runtime: Int? = null,
    var tagline: String? = null,
    var homepage: String? = null,
    val originalLanguage: String? = null,
    var genresName: List<String> = emptyList(),
    val createdAt: Date = Calendar.getInstance().time,
    var detailLoaded: Boolean = false
)