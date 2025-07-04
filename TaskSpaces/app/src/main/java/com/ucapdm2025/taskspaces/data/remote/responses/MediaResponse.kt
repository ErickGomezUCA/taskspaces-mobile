package com.ucapdm2025.taskspaces.data.remote.responses

data class MediaResponse(
    val id: Int,
    val filename: String,
    val type: String,
    val url: String,
    val authorId: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null
    )