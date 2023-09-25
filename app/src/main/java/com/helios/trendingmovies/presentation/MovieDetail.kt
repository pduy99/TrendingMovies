package com.helios.trendingmovies.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoAdultContent
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.helios.trendingmovies.common.HyperlinkText
import com.helios.trendingmovies.common.RatingBar
import com.helios.trendingmovies.model.MovieDetail
import com.helios.trendingmovies.utils.formattedYear
import com.helios.trendingmovies.utils.minuteToTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    uiState: AppUiState,
    onBackPress: () -> Unit
) {
    val errorMessage = uiState.errorMessage?.getContentOrNull()
    BackHandler {
        onBackPress()
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(uiState.currentSelectedMovie?.title ?: "Details")
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackPress()
                    })
            }
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                innerPadding
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                uiState.currentSelectedMovie?.let {
                    LazyColumn(content = {
                        item { ItemPoster(it) }
                        item { ItemTitle(it) }
                        item { ItemOverview(it) }
                        item { ItemInformation(it) }
                    })
                }
            }
        }
    }
}


@Composable
fun ItemPoster(movieDetail: MovieDetail) {
    Box(modifier = Modifier.padding(horizontal = 15.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movieDetail.getFullImagePath(movieDetail.backdropPath ?: "")).crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 60.dp)
                .clip(shape = RoundedCornerShape(10.dp))
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movieDetail.getFullImagePath(movieDetail.posterPath ?: "")).crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(120.dp)
                .height(160.dp)
                .align(Alignment.CenterStart)
                .clip(shape = RoundedCornerShape(10.dp))
        )
    }
}

@Composable
@Preview
fun ItemTitlePreview() {
    ItemTitle(
        MovieDetail(
            title = "No One Will Save You",
            voteAverage = 8.0,
            releaseDate = "2023-09-22",
            originalTitle = "EN"
        )
    )
}

@Composable
fun ItemTitle(movieDetail: MovieDetail) {

    Spacer(modifier = Modifier.height(20.dp))

    val title = movieDetail.title ?: ""
    Text(
        text = buildAnnotatedString {
            append(title); append(" ");withStyle(style = SpanStyle(color = Color.Gray)) {
            append(
                "(${formattedYear(movieDetail.releaseDate)})"
            )
        }
        },
        style = MaterialTheme.typography.headlineMedium,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "R",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.padding(end = 10.dp)
        )

        val originalLanguage = if (movieDetail.originalLanguage != null) {
            " (${movieDetail.originalLanguage!!.uppercase(Locale.ROOT)})"
        } else ""
        Text(
            text = movieDetail.releaseDate + originalLanguage + " " + movieDetail.runtime?.let {
                minuteToTime(it)
            },
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.padding(end = 10.dp)
        )
    }


    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        content = {
            movieDetail.genres.forEach {
                item {
                    Text(
                        text = if (it == movieDetail.genres.last()) it.name else it.name + ", ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
        })

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "User Score",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 10.dp, end = 15.dp)
        )
        RatingBar(
            (movieDetail.voteAverage?.toFloat()?.div(2)) ?: 0f, modifier = Modifier
                .height(25.dp)
                .wrapContentSize()
        )
    }
}

@Composable
fun ItemOverview(movieDetail: MovieDetail) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = "Overview",
        style = MaterialTheme.typography.headlineMedium,
        maxLines = 1,
        modifier = Modifier.padding(start = 15.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    val lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 4 / 3
    Text(
        text = movieDetail.overview ?: "",
        style = MaterialTheme.typography.bodyLarge,
        lineHeight = lineHeight,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
}

@Composable
fun ItemInformation(movieDetail: MovieDetail) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = "Information",
        style = MaterialTheme.typography.headlineMedium,
        maxLines = 1,
        modifier = Modifier.padding(start = 15.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    movieDetail.homepage?.let {
        Row(modifier = Modifier.padding(horizontal = 15.dp)) {
            Icon(imageVector = Icons.Default.Home, null)
            Spacer(modifier = Modifier.width(10.dp))
            HyperlinkText(fullText = it, linkText = listOf(it), hyperlinks = listOf(it))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    movieDetail.adult?.let {
        Row(modifier = Modifier.padding(horizontal = 15.dp)) {
            Icon(imageVector = Icons.Default.NoAdultContent, null)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Adult content: $it")
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    movieDetail.tagline?.let {
        Row(modifier = Modifier.padding(horizontal = 15.dp)) {
            Icon(imageVector = Icons.Default.Tag, null)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = it)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}


