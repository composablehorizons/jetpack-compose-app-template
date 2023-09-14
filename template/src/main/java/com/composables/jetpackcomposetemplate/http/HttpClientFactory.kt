package com.composables.jetpackcomposetemplate.http

import io.ktor.client.*

interface HttpClientFactory {
    fun createHttpClient(): HttpClient
}
