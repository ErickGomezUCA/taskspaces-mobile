package com.ucapdm2025.taskspaces.data.repository.task

import android.util.Log
import com.ucapdm2025.taskspaces.data.dummy.assignedTasksDummies
import com.ucapdm2025.taskspaces.data.dummy.tasksDummies
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class TaskRepositoryImpl : TaskRepository {
    private val tasks = MutableStateFlow(tasksDummies)
    private val bookmarkedTasks =
        MutableStateFlow(com.ucapdm2025.taskspaces.data.dummy.bookmarkedTasksDummies)
    private val assignedTasks = MutableStateFlow(assignedTasksDummies)

    private var autoIncrementId = tasks.value.size + 1;

    override fun getTasksByProjectId(projectId: Int): Flow<List<TaskModel>> {
        return tasks.map { list -> list.filter { it.projectId == projectId } }
    }

    override fun getBookmarkedTasks(): Flow<List<TaskModel>> {
        return bookmarkedTasks.asStateFlow()
    }

    override fun getAssignedTasks(userId: Int): Flow<List<TaskModel>> {
        return assignedTasks.asStateFlow()
    }

    override fun getTaskById(id: Int): Flow<TaskModel?> {
        return tasks.map { list -> list.find { it.id == id } }
    }

    override suspend fun createTask(
        title: String,
        description: String,
        deadline: LocalDateTime,
        status: StatusVariations,
        breadcrumb: String,
        projectId: Int
    ): TaskModel {
        val createdTaskModel = TaskModel(
            id = autoIncrementId++,
            title = title,
            description = description,
            deadline = deadline,
            status = status,
            breadcrumb = breadcrumb,
            tags = emptyList(), // Assuming no tags are set initially
            assignedMembers = emptyList(), // Assuming no members are assigned initially
            comments = emptyList(),
            projectId = projectId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        tasks.value = tasks.value + createdTaskModel
        return createdTaskModel
    }

    override suspend fun updateTask(
        id: Int,
        title: String,
        description: String,
        deadline: LocalDateTime,
        status: StatusVariations,
        breadcrumb: String,
        projectId: Int
    ): TaskModel {
        val updatedTaskModel = TaskModel(
            id = id,
            title = title,
            description = description,
            deadline = deadline,
            status = status,
            breadcrumb = breadcrumb,
            tags = emptyList(), // Assuming no tags are set initially
            assignedMembers = emptyList(), // Assuming no members are assigned initially
            comments = emptyList(),
            projectId = projectId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        tasks.value = tasks.value.map {
            if (it.id == updatedTaskModel.id) updatedTaskModel else it
        }

        Log.d("test1", tasks.value.toString())

        return updatedTaskModel
    }

    override suspend fun deleteTask(id: Int): Boolean {
        val exists = tasks.value.any { it.id == id }

        if (exists) {
            tasks.value = tasks.value.filter { it.id != id }
        }

        return exists
    }

    override suspend fun bookmarkTask(id: Int): Boolean {
        val task = tasks.value.find { it.id == id } ?: return false

        if (bookmarkedTasks.value.any { it.id == id }) {
            bookmarkedTasks.value = bookmarkedTasks.value.filter { it.id != id }
        } else {
            bookmarkedTasks.value = bookmarkedTasks.value + task
        }

        return true
    }
}