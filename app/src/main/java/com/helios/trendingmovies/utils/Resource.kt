package com.helios.trendingmovies.utils

/**
 * Created by phpduy99 on 24/09/2023
 */
sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error(val throwable: Throwable) : Resource<Nothing>()
}