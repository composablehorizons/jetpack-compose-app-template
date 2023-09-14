package com.composables.jetpackcomposetemplate.apimodel

import kotlinx.serialization.Serializable

@Serializable
data class ApiPhoto(
    val id: String,
    val description: String?,
    val alt_description: String,
    val urls: Urls,
    val views: Long,
)