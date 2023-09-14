package com.composables.jetpackcomposetemplate

import android.app.Application
import com.composables.jetpackcomposetemplate.core.AppReference
import com.composables.jetpackcomposetemplate.details.PhotoDetailsViewModel
import com.composables.jetpackcomposetemplate.di.httpClientFactory
import com.composables.jetpackcomposetemplate.home.HomeScreenViewModel
import com.composables.jetpackcomposetemplate.home.PhotoId
import com.composables.jetpackcomposetemplate.permissions.AndroidSystemPermissions
import com.composables.jetpackcomposetemplate.permissions.SystemPermissions

fun applicationContext(): Application {
    return AppReference.requireApplication()
}

fun homeScreenViewModel(): HomeScreenViewModel {
    return HomeScreenViewModel(photosRepository())
}

fun photosRepository(): PhotosRepository {
    val client = httpClientFactory().createHttpClient()
    return UnsplashPhotosRepository(client)
}


fun photoDetailsViewModel(photoId: PhotoId): PhotoDetailsViewModel {
    return PhotoDetailsViewModel(photoId = photoId, photosRepository = photosRepository())
}