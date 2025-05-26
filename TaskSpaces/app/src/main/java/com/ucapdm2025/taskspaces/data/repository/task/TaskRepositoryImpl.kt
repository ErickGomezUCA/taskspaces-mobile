package com.ucapdm2025.taskspaces.data.repository.task

import com.ucapdm2025.taskspaces.data.dummy.tasksDummy
import com.ucapdm2025.taskspaces.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskRepositoryImpl: TaskRepository {
    private val tasks = MutableStateFlow(tasksDummy)

    override fun getTasks(): Flow<List<Task>> {
        return tasks.asStateFlow()
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
}