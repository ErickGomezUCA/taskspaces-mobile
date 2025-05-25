package com.ucapdm2025.taskspaces.data.model

data class Task (
    override val id: Int,
    val title: String,
    val description: String,
    val date: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    val timer: Int,
    val status: String, // TODO: Set enum class for status
    val tags: List<Tag>,
    val assignedMembers: List<User>,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)