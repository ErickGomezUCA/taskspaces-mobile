package com.ucapdm2025.taskspaces.data.remote.responses

import com.google.gson.annotations.SerializedName

/**
 * BaseResponse is a generic data class that represents the structure of API responses.
 * It includes a status code, a message, and the content of the response.
 *
 * @param T The type of the content in the response.
 * @property status The HTTP status code of the response.
 * @property message A message providing additional information about the response.
 * @property content The actual content of the response, which can be of any type.
 */
data class BaseResponse<T>(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("content")
    val content: T
)