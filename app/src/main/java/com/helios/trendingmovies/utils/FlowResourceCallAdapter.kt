package com.helios.trendingmovies.utils

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

/**
 * Created by phpduy99 on 24/09/2023
 */

private const val TAG = "FlowResourceCallAdapter"

class FlowResourceCallAdapter<R>(
    private val responseType: Type,
    private val isSelfExceptionHandling: Boolean
) : CallAdapter<R, Flow<Resource<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>): Flow<Resource<R>> = flow {

        // Firing loading resource
        Log.i(TAG, "Emit loading")
        emit(Resource.Loading)

        val resp = call.awaitResponse()

        Log.i(TAG, "resp.isSuccessful: ${resp.isSuccessful}")
        if (resp.isSuccessful) {
            resp.body()?.let { data ->
                // Success
                emit(Resource.Success(data))
            } ?: kotlin.run {
                // Error
                emit(Resource.Error(Throwable(resp.message())))
            }
        } else {
            // Error
            val errorBody = resp.message()
            emit(Resource.Error(Throwable(errorBody)))
        }

    }.catch { error ->
        if (isSelfExceptionHandling) {
            emit(Resource.Error(error))
        } else {
            throw error
        }
    }
}