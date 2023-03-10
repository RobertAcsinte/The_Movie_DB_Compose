package com.example.themoviedb.data.remote.dto

import com.squareup.moshi.Json

data class SessionIdGuest(
    @field:Json(name = "guest_session_id")
    val sessionIdGuest: String?,
)