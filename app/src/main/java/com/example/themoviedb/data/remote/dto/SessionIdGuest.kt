package com.example.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SessionIdGuest(
    @SerializedName("guest_session_id")
    val sessionIdGuest: String?,
)