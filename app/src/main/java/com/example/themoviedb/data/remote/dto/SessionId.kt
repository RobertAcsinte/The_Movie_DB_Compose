package com.example.themoviedb.data.remote.dto

import com.squareup.moshi.Json


data class SessionId(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "session_id")
    val sessionId: String?,
    @field:Json(name = "status_message")
    val statusMessage: String?
)
