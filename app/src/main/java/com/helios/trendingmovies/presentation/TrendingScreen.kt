package com.helios.trendingmovies.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.helios.trendingmovies.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    modifier: Modifier,
    uiState: AppUiState,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    atBottomList: () -> Unit,
) {

    var searchActive by remember {
        mutableStateOf(false)
    }
    val movies = uiState.movies
    val errorMessage = uiState.errorMessage?.getContentOrNull()
    val gridScrollState = rememberLazyGridState()
    LaunchedEffect(gridScrollState.isScrollInProgress && !gridScrollState.canScrollForward) {
        if (movies.isNotEmpty()) {
            atBottomList()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(uiState.title)
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .heightIn(
                    0.dp,
                    65.dp
                ), // trick to not show the search bar content when it is active
                query = uiState.searchText,
                onQueryChange = {
                    onQueryChange(it)
                },
                onSearch = {
                    searchActive = false
                },
                active = searchActive,
                onActiveChange = {
                    searchActive = it
                },
                placeholder = {
                    Text(text = "Search movie")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchActive) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier.clickable {
                                if (uiState.searchText.isNotEmpty()) {
                                    onQueryChange("")
                                } else {
                                    searchActive = false
                                }
                            })
                    }
                },
                content = { }
            )
            LazyVerticalGrid(
                state = gridScrollState,
                columns = GridCells.Adaptive(180.dp),
                modifier = modifier.padding(8.dp),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(movies) { movie ->
                    MovieCard(movie) {
                        onMovieClick(it)
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier, onMovieClick: (movieId: Int) -> Unit) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = { onMovieClick(movie.id) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(movie.getFullImagePath(movie.posterPath ?: movie.backdropPath ?: ""))
                    .crossfade(true).build(),
                error = null,
                placeholder = null,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Year: ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Vote: ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}