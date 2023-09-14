package com.composables.jetpackcomposetemplate.home

import androidx.lifecycle.viewModelScope
import co.composables.jetpackcomposetemplate.PhotosRepository
import com.composables.jetpackcomposetemplate.core.AbstractViewModel
import kotlinx.coroutines.launch


typealias PhotoId = String

data class Photo(
    val url: String,
    val description: String,
    val views: Long,
    val id: PhotoId,
)

sealed interface HomeScreenState {
    object Loading : HomeScreenState
    data class Loaded(val photos: List<Photo>) : HomeScreenState
    object Error : HomeScreenState
}

class HomeScreenViewModel(
    private val photosRepository: PhotosRepository,
) : AbstractViewModel<HomeScreenState, Nothing, Unit>(
    startWith = HomeScreenState.Loading
) {

    init {
        viewModelScope.launch {
            val photos = photosRepository.loadAllPhotos()
            if (photos == null) {
                emitState(HomeScreenState.Error)
            } else {
                emitState(HomeScreenState.Loaded(photos))
            }
        }
    }
}
