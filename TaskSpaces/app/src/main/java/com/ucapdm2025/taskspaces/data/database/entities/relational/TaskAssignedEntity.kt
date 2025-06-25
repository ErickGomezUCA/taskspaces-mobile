package com.ucapdm2025.taskspaces.data.database.entities.relational

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.relational.TaskAssignedModel

@Entity(tableName = "task_assigned", primaryKeys = ["userId", "taskId"])
data class TaskAssignedEntity (
    val userId: Int,
    val taskId: Int,
    val createdAt: String? = null
)

fun TaskAssignedEntity.toDomain(): TaskAssignedModel {
    return TaskAssignedModel(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}