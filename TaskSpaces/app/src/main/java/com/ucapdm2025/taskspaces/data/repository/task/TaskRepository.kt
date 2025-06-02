package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksByProjectId(projectId: Int): Flow<List<Task>>
    fun getBookmarkedTasks(): Flow<List<Task>>
    fun getAssignedTasks(userId: Int): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Task?
    suspend fun createTask(
        title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): Task

    suspend fun updateTask(
        id: Int,
        title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): Task

    suspend fun deleteTask(id: Int): Boolean
    suspend fun bookmarkTask(id: Int): Boolean
}