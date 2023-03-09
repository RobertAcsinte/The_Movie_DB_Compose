package com.example.themoviedb.data.remote.dto

import com.squareup.moshi.Json


data class RequestToken(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "request_token")
    val requestToken: String?,
    @field:Json(name = "status_message")
    val statusMessage: String?
)

