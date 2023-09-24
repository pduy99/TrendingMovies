package com.helios.trendingmovies.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.helios.trendingmovies.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    var searchActive by remember {
        mutableStateOf(false)
    }
    val movies = uiState.movies

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(uiState.title)
                    }
                )
            }

        },
    ) { innerPadding ->
        if (uiState.isShowingHomePage) {
            Column(modifier = modifier.padding(innerPadding)) {
                SearchBar(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .heightIn(0.dp, 65.dp), // trick to not show the search bar content when it is active
                    query = uiState.searchText,
                    onQueryChange = {
                        viewModel.onQueryChanged(it)
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
                                        viewModel.onQueryChanged("")
                                    } else {
                                        searchActive = false
                                    }
                                })
                        }
                    },
                    content = {  }
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    modifier = modifier.padding(8.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(movies) { movie ->
                        MovieCard(movie)
                    }
                }
            }

        } else {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.width(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = { },
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
                Text(text = movie.title!!, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
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