package com.helios.trendingmovies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helios.trendingmovies.data.repository.MovieRepository
import com.helios.trendingmovies.model.Movie
import com.helios.trendingmovies.model.MovieDetail
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
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    init {
        getTrendingMovies()
    }

    fun getTrendingMovies() {
        movieRepository.getTrendingMovies().onStart {
            _uiState.value = _uiState.value.copy(title = "Trending movies")
        }.catch {
            _uiState.value = _uiState.value.copy(errorMessage = Event(it.message ?: "Something went wrong"), isLoading = false)
        }.onEach {resource ->
            when(resource) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, movies = resource.data.movies)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, toastMessage = Event(resource.throwable.message ?: "Something went wrong"))
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
                _uiState.value = _uiState.value.copy(errorMessage = Event(it.message ?: "Something went wrong"), isLoading = false)
            }.onEach {resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, movies = resource.data.movies)
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, toastMessage = Event(resource.throwable.message ?: "Something went wrong"))
                    }
                }
            }.launchIn(viewModelScope)
        }
        else {
            getTrendingMovies()
        }
    }

    fun resetHomeScreenState() {
        _uiState.value = _uiState.value.copy(isShowingHomePage = true, currentSelectedMovie = null)
    }

    fun onMovieClicked(movieId: Int) {
        movieRepository.getMovieDetail(movieId).onStart {
            _uiState.value = _uiState.value.copy(isShowingHomePage = false)
        }.catch {
            _uiState.value = _uiState.value.copy(errorMessage = Event(it.message ?: "Something went wrong"), isLoading = false)
        }.onEach {resource ->
            when(resource) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, currentSelectedMovie = resource.data)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, toastMessage = Event(resource.throwable.message ?: "Something went wrong"))
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class AppUiState(
    val movies: List<Movie> = emptyList(),
    val currentSelectedMovie : MovieDetail? = null,
    val isShowingHomePage: Boolean = true,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val toastMessage: Event<String>? = null,
    val errorMessage: Event<String>? = null,
    val title: String = ""
)