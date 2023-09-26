package com.helios.trendingmovies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helios.trendingmovies.data.repository.MovieRepository
import com.helios.trendingmovies.domain.model.Movie
import com.helios.trendingmovies.utils.Event
import com.helios.trendingmovies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class TrendingMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    init {
        getTrendingMovies(false)
    }

    fun getTrendingMovies(fetchRemote: Boolean) {
        movieRepository.getTrendingMovies(fetchRemote).onStart {
            _uiState.value = _uiState.value.copy(title = "Trending movies")
        }.catch {
            Log.e(TAG, "Error: ${it.printStackTrace()}")
            _uiState.value = _uiState.value.copy(
                errorMessage = Event(it.message ?: "Something went wrong"),
                isLoading = false
            )
        }.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, movies = resource.data)
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = Event(resource.throwable.message ?: "Something went wrong")
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchText = query)
        if (query.isNotBlank()) {
            movieRepository.searchMovie(query).onStart {
                _uiState.value = _uiState.value.copy(title = "Search results")
            }.catch {
                _uiState.value = _uiState.value.copy(
                    errorMessage = Event(it.message ?: "Something went wrong"),
                    isLoading = false
                )
            }.onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, movies = resource.data)
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = Event(
                                resource.throwable.message ?: "Something went wrong"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            getTrendingMovies(false)
        }
    }

    fun resetHomeScreenState() {
        _uiState.value = _uiState.value.copy(isShowingHomePage = true)
    }

    fun onMovieClicked(movieId: Int) {
        movieRepository.getMovieDetail(movieId).onStart {
            _uiState.value = _uiState.value.copy(isShowingHomePage = false)
        }.catch {
            Log.e(TAG, "Error: ${it.message}")
            _uiState.value = _uiState.value.copy(
                errorMessage = Event(it.message ?: "Something went wrong"),
                isLoading = false
            )
        }.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, currentSelectedMovie = resource.data)
                }

                is Resource.Error -> {
                    Log.e(TAG, "Error: ${resource.throwable.message}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = Event(resource.throwable.message ?: "Something went wrong")
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

private const val TAG = "TrendingMovieViewModel"

data class AppUiState(
    val movies: List<Movie> = emptyList(),
    val currentSelectedMovie: Movie? = null,
    val isShowingHomePage: Boolean = true,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val errorMessage: Event<String>? = null,
    val title: String = ""
)