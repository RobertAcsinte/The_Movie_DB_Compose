package com.example.themoviedb.data.remote.dto

import com.squareup.moshi.Json


data class SessionId(
    @field:Json(name = "success")
    val success: Boolean,
    @field:Json(name = "sesion_id")
    val sessionId: String?
)
