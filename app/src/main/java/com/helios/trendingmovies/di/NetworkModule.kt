package com.helios.trendingmovies.di

import com.helios.trendingmovies.BuildConfig
import com.helios.trendingmovies.data.remote.MovieApiService
import com.helios.trendingmovies.data.remote.interceptor.ApiKeyInterceptor
import com.helios.trendingmovies.utils.FlowResourceCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by phpduy99 on 24/09/2023
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val TIMEOUT = 15L

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
    }.build()

    private val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpClient)
        addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        addCallAdapterFactory(FlowResourceCallAdapterFactory())
    }.build()

    @Provides
    fun providesMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)
}