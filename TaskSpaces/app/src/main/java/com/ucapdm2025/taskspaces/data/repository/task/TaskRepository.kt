package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * TaskRepository is an interface that defines the methods for managing tasks in the application.
 * It provides methods to get tasks by project ID, get bookmarked tasks, get assigned tasks,
 * get a task by its ID, create a new task, update an existing task, delete a task, and bookmark a task.
 */
interface TaskRepository {
    fun getTasksByProjectId(projectId: Int): Flow<Resource<List<TaskModel>>>
    fun getAssignedTasks(userId: Int): Flow<Resource<List<TaskModel>>>
    fun getTaskById(id: Int): Flow<Resource<TaskModel?>>
    suspend fun createTask(
        title: String,
        description: String? = null,
        deadline: LocalDateTime? = null,
        timer: Float? = null,
        status: StatusVariations = StatusVariations.PENDING,
        projectId: Int
    ): Result<TaskModel>
    suspend fun updateTask(
        id: Int,
        title: String,
        description: String? = null,
        deadline: LocalDateTime? = null,
        timer: Float? = null,
        status: StatusVariations = StatusVariations.PENDING,
    ): Result<TaskModel>
    suspend fun deleteTask(id: Int): Result<TaskModel>
// Members
    fun getAssignedMembersByTaskId(taskId: Int): Flow<Resource<List<UserModel>>>
    fun getWorkspaceMembersByTaskId(taskId: Int): Flow<Resource<List<UserModel>>>
    suspend fun assignMemberToTask(
        taskId: Int,
        userId: Int,
    ): Result<TaskModel>
    suspend fun unassignMemberFromTask(
        taskId: Int,
        userId: Int
    ): Result<TaskModel>
}