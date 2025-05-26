package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Task?
    suspend fun createTask(task: Task): Task
    suspend fun updateTask(task: Task): Task
    suspend fun deleteTask(id: Int): Boolean
}