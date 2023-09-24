package com.helios.trendingmovies.utils

class Event<out T>(private val content: T) {

    private var hasBeenHandled: Boolean = false

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}