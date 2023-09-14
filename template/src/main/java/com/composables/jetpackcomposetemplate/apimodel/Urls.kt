package com.composables.jetpackcomposetemplate.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    @SerialName("small") val small: String,
)