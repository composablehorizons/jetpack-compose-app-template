package com.composables.jetpackcomposetemplate.details

import androidx.lifecycle.viewModelScope
import com.composables.jetpackcomposetemplate.PhotosRepository
import com.composables.jetpackcomposetemplate.core.AbstractViewModel
import com.composables.jetpackcomposetemplate.home.Photo
import com.composables.jetpackcomposetemplate.home.PhotoId
import kotlinx.coroutines.launch


sealed interface PhotoDetailsState {
    object Loading : PhotoDetailsState
    data class Loaded(val photo: Photo, val favorite: Boolean) : PhotoDetailsState
    object Error : PhotoDetailsState
}

class PhotoDetailsViewModel(
    private val photoId: PhotoId,
    private val photosRepository: PhotosRepository,
) : AbstractViewModel<PhotoDetailsState, Nothing, Nothing>(PhotoDetailsState.Loading) {
    init {
        viewModelScope.launch {
            val photo = photosRepository.lookupPhoto(photoId)
            val state = if (photo == null) {
                PhotoDetailsState.Error
            } else {
                PhotoDetailsState.Loaded(photo, favorite = false)
            }
            emitState(state)
        }
    }
}
