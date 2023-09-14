package com.composables.jetpackcomposetemplate.home

import androidx.compose.runtime.Composable
import com.composables.jetpackcomposetemplate.destinations.PhotoDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@RootNavGraph(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    HomeTab(
        onPhotoClick = { photo ->
            navigator.navigate(PhotoDetailsScreenDestination(photo.id))
        }
    )
}

