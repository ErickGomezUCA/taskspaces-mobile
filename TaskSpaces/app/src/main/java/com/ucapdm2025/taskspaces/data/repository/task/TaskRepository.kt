package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksByProjectId(projectId: Int): Flow<List<TaskModel>>
    fun getBookmarkedTasks(): Flow<List<TaskModel>>
    fun getAssignedTasks(userId: Int): Flow<List<TaskModel>>
    fun getTaskById(id: Int): Flow<TaskModel?>
    suspend fun createTask(
        title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): TaskModel

    suspend fun updateTask(
        id: Int,
        title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): TaskModel

    suspend fun deleteTask(id: Int): Boolean
    suspend fun bookmarkTask(id: Int): Boolean
}