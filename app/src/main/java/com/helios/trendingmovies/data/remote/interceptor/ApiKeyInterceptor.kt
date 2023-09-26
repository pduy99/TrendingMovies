package com.helios.trendingmovies.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by phpduy99 on 24/09/2023
 */
class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .url(request.url.newBuilder().addQueryParameter("api_key", apiKey).build())
            .build()

        return chain.proceed(newRequest)
    }
}