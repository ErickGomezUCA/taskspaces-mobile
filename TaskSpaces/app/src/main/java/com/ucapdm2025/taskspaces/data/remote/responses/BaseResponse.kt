package com.ucapdm2025.taskspaces.data.remote.responses

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("content")
    val content: T
)