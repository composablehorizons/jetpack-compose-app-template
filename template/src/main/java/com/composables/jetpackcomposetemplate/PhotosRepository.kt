package co.composables.jetpackcomposetemplate

import com.composables.jetpackcomposetemplate.apimodel.ApiPhoto
import com.composables.jetpackcomposetemplate.home.Photo
import com.composables.jetpackcomposetemplate.home.PhotoId
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface PhotosRepository {
    suspend fun loadAllPhotos(): List<Photo>?
    suspend fun lookupPhoto(id: PhotoId): Photo?
}

fun main(): Unit = runBlocking {
    val repo = photosRepository()
    repo.loadAllPhotos()
}

class UnsplashPhotosRepository(
    private val client: HttpClient,
) : PhotosRepository {

    override suspend fun loadAllPhotos(): List<Photo>? = withContext(Dispatchers.IO) {
        val result = kotlin.runCatching {
            val response = client.get(
                "https://api.unsplash.com/photos/random?query=landscape&count=100"
            ) {
                headers {
                    append("Authorization", "Client-ID $UNSPLASH_API_KEY")
                }
            }

            response
                .body<List<ApiPhoto>>()
                .map {
                    Photo(
                        url = it.urls.small,
                        description = it.description ?: it.alt_description,
                        id = it.id,
                        views = it.views
                    )
                }
        }
        result.getOrNull()
    }

    override suspend fun lookupPhoto(id: PhotoId): Photo? {
        return kotlin.runCatching {
            val response = client.get(
                "https://api.unsplash.com/photos/${id}"
            ) {
                headers {
                    append("Authorization", "Client-ID $UNSPLASH_API_KEY")
                }
            }

            response
                .body<ApiPhoto>()
                .let {
                    Photo(
                        url = it.urls.small,
                        description = it.description ?: it.alt_description,
                        id = it.id,
                        views = it.views
                    )
                }
        }.getOrNull()
    }

    private companion object {
        // Key for demo purposes only. Do not use this key in your own apps
        // Get your own key at https://unsplash.com/developers
        const val UNSPLASH_API_KEY = "3atFjlj2qw8x82B2LSvBkX6m_xn7BK9f8xotBBE2M4w"
    }
}