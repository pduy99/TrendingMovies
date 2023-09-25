package com.helios.trendingmovies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: TrendingMovieViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    // Add the AnimatedVisibility composable to animate the visibility of the child composable
    AnimatedVisibility(
        visible = uiState.isShowingHomePage,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ), initialOffsetX = { -300 }) + fadeIn(animationSpec = tween(300)),
        exit = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ), targetOffsetX = { -300 }) + fadeOut(animationSpec = tween(300))
    ) {
        TrendingScreen(
            modifier = modifier,
            uiState = uiState,
            onQueryChange = viewModel::onQueryChanged,
            onMovieClick = viewModel::onMovieClicked
        )
    }

    AnimatedVisibility(
        visible = !uiState.isShowingHomePage,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ), initialOffsetX = { 300 }) + fadeIn(animationSpec = tween(300)),
        exit = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ), targetOffsetX = { 300 }) + fadeOut(animationSpec = tween(300))
    ) {
        MovieDetailsScreen(uiState = uiState, viewModel::resetHomeScreenState)
    }

//    if (uiState.isShowingHomePage) {
//        TrendingScreen(
//            modifier = modifier,
//            uiState = uiState,
//            onQueryChange = viewModel::onQueryChanged,
//            onMovieClick = viewModel::onMovieClicked
//        )
//    } else {
//        MovieDetailsScreen(uiState = uiState, viewModel::resetHomeScreenState)
//    }
}





