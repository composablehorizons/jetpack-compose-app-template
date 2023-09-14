package com.composables.jetpackcomposetemplate.di

import com.composables.jetpackcomposetemplate.http.HttpClientFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val httpClient = object : HttpClientFactory {
    override fun createHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}

fun httpClientFactory(): HttpClientFactory {
    return httpClient
}
