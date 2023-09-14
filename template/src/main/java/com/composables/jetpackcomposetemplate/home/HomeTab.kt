package com.composables.jetpackcomposetemplate.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.composables.jetpackcomposetemplate.formatLongNumber
import co.composables.jetpackcomposetemplate.homeScreenViewModel
import com.composables.jetpackcomposetemplate.R
import com.composables.jetpackcomposetemplate.ui.ContentCard
import com.composables.jetpackcomposetemplate.ui.LoadingScreen

@Composable
fun HomeTab(
    onPhotoClick: (Photo) -> Unit,
) {
    val homeViewModel = viewModel { homeScreenViewModel() }

    val state by homeViewModel.state.collectAsState()

    when (val currentState = state) {
        is HomeScreenState.Loaded -> LoadedScreen(
            currentState.photos,
            onPhotoClick = onPhotoClick
        )

        HomeScreenState.Loading -> LoadingScreen("loading-home")
        HomeScreenState.Error -> ErrorScreen()
    }
}

@Composable
private fun ErrorScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Couldn't load photos right now",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Try again in a while",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun LoadedScreen(photos: List<Photo>, onPhotoClick: (Photo) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(photos) { photo ->
            ContentCard(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = photo.url,
                onClick = {
                    onPhotoClick(photo)
                },
                imageContentDescription = photo.description,
                title = photo.description,
                maxLines = 3,
                textOverflow = TextOverflow.Ellipsis,
                subtitle = stringResource(R.string.views_x, formatLongNumber(photo.views))
            )
        }
    }
}
