package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import java.time.LocalDateTime

data class TaskModel (
    override val id: Int,
    val breadcrumb: String = "",
    val title: String,
    val description: String? = null,
    val deadline: LocalDateTime? = null, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    val status: StatusVariations = StatusVariations.PENDING, // TODO: Set enum class for status
    val tags: List<TagModel> = emptyList<TagModel>(), // TODO: Separate this attribute when inserting to the db
    val assignedMembers: List<UserModel> = emptyList<UserModel>(), // TODO: Separate this attribute when inserting to the db
    val comments: List<CommentModel> = emptyList<CommentModel>(), // TODO: Separate this attribute when inserting to the db
    val projectId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = "",
): BaseModel(id, createdAt, updatedAt)