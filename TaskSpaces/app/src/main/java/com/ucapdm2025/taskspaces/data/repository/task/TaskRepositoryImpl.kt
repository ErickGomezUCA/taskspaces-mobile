package com.ucapdm2025.taskspaces.data.repository.task

import android.util.Log
import com.ucapdm2025.taskspaces.data.dummy.assignedTasksDummy
import com.ucapdm2025.taskspaces.data.dummy.tasksDummy
import com.ucapdm2025.taskspaces.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class TaskRepositoryImpl : TaskRepository {
    private val tasks = MutableStateFlow(tasksDummy)
    private val bookmarkedTasks =
        MutableStateFlow(com.ucapdm2025.taskspaces.data.dummy.bookmarkedTasksDummy)
    private val assignedTasks = MutableStateFlow(assignedTasksDummy)

    private var autoIncrementId = tasks.value.size + 1;

    override fun getTasksByProjectId(projectId: Int): Flow<List<Task>> {
        return tasks.map { list -> list.filter { it.projectId == projectId } }
    }

    override fun getBookmarkedTasks(): Flow<List<Task>> {
        return bookmarkedTasks.asStateFlow()
    }

    override fun getAssignedTasks(userId: Int): Flow<List<Task>> {
        return assignedTasks.asStateFlow()
    }

    override fun getTaskById(id: Int): Flow<Task?> {
        return tasks.map { list -> list.find { it.id == id } }
    }

    override suspend fun createTask(
        title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): Task {
        val createdTask = Task(
            id = autoIncrementId++,
            title = title,
            description = description,
            deadline = deadline,
            status = status,
            breadcrumb = breadcrumb,
            projectId = projectId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        tasks.value = tasks.value + createdTask
        return createdTask
    }

    override suspend fun updateTask(
        id: Int, title: String,
        description: String,
        deadline: String,
        status: String,
        breadcrumb: String,
        projectId: Int
    ): Task {
        val updatedTask = Task(
            id = id,
            title = title,
            description = description,
            deadline = deadline,
            status = status,
            breadcrumb = breadcrumb,
            projectId = projectId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        tasks.value = tasks.value.map {
            if (it.id == updatedTask.id) updatedTask else it
        }

        Log.d("test1", tasks.value.toString())

        return updatedTask
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