package com.ucapdm2025.taskspaces.data.model

data class Task (
    override val id: Int,
    val breadcrumb: String,
    val title: String,
    val description: String,
    val deadline: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    val status: String, // TODO: Set enum class for status
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)