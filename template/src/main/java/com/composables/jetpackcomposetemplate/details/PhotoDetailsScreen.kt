package com.composables.jetpackcomposetemplate.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composables.jetpackcomposetemplate.R
import com.composables.jetpackcomposetemplate.destinations.HomeScreenDestination
import com.composables.jetpackcomposetemplate.destinations.PhotoDetailsScreenDestination
import com.composables.jetpackcomposetemplate.formatLongNumber
import com.composables.jetpackcomposetemplate.home.PhotoId
import com.composables.jetpackcomposetemplate.photoDetailsViewModel
import com.composables.jetpackcomposetemplate.ui.LoadingScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun PhotoDetailsScreen(
    photoId: PhotoId,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel { photoDetailsViewModel(photoId) }

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        when (val currentState = state) {
            is PhotoDetailsState.Loaded -> PhotoDetailsContents(
                state = currentState,
                onNavigateUpClick = { navigator.navigateUp() }
            )

            PhotoDetailsState.Loading -> LoadingScreen()
            PhotoDetailsState.Error -> LaunchedEffect(Unit) {
                Toast.makeText(context, "Could not find photo", Toast.LENGTH_SHORT).show()
                navigator.navigate(HomeScreenDestination) {
                    popUpTo(PhotoDetailsScreenDestination.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoDetailsContents(state: PhotoDetailsState.Loaded, onNavigateUpClick: () -> Unit) {
    val imageUrl = state.photo.url
    val photo = state.photo
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = photo.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
                actions = {
                    IconButton(onClick = {}) {
                        val isFavorite = state.favorite
                        if (isFavorite) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                            )
                        } else {
                            Icon(
                                Icons.TwoTone.Favorite,
                                contentDescription = "Not favorite",
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUpClick) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                })
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = photo.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(16 / 9f)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Views", style = MaterialTheme.typography.bodySmall)
                Text(stringResource(R.string.views_x, formatLongNumber(photo.views)))
                Spacer(Modifier.height(24.dp))
            }
        }

    }
}
