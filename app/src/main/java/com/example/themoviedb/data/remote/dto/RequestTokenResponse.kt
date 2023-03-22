package com.example.themoviedb.data.remote.dto

import com.google.gson.annotations.SerializedName


data class RequestTokenResponse(
    val success: Boolean,
    @SerializedName("request_token")
    val requestToken: String?,
    @SerializedName("status_message")
    val statusMessage: String?
)

