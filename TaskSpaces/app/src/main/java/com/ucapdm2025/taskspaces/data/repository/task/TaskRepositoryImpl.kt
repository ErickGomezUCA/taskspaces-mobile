package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.dummy.assignedTasksDummy
import com.ucapdm2025.taskspaces.data.dummy.tasksDummy
import com.ucapdm2025.taskspaces.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl: TaskRepository {
    private val tasks = MutableStateFlow(tasksDummy)
    private val bookmarkedTasks = MutableStateFlow(com.ucapdm2025.taskspaces.data.dummy.bookmarkedTasksDummy)
    private val assignedTasks = MutableStateFlow(assignedTasksDummy)

    override fun getTasksByProjectId(projectId: Int): Flow<List<Task>> {
        return tasks.map {list -> list.filter {it.projectId == projectId }}
    }

    override fun getBookmarkedTasks(): Flow<List<Task>> {
        return bookmarkedTasks.asStateFlow()
    }

    override fun getAssignedTasks(userId: Int): Flow<List<Task>> {
        return assignedTasks.asStateFlow()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return tasks.value.find { it.id == id }
    }

    override suspend fun createTask(task: Task): Task {
        tasks.value = tasks.value + task
        return task
    }

    override suspend fun updateTask(task: Task): Task {
        tasks.value = tasks.value.map {
            if (it.id == task.id) task else it
        }
        return task
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