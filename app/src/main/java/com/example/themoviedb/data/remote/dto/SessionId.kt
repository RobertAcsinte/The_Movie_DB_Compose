package com.example.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName


data class SessionId(
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String?,
    @SerializedName("status_message")
    val statusMessage: String?
)
