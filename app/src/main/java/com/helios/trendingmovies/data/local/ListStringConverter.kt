package com.helios.trendingmovies.data.local

import androidx.room.TypeConverter

class ListStringConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return value.joinToString(separator = ",")
    }
}