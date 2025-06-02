package com.ucapdm2025.taskspaces.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    val taskRepository: TaskRepository = TaskRepositoryImpl()
    val id = 1;

    private val _task = MutableStateFlow<Task>(Task(
        id = 0,
        breadcrumb = "",
        title = "",
        description = "",
        deadline = "",
        status = "PENDING",
        projectId = 0,
        createdAt = "",
        updatedAt = ""
    ))
    val task: StateFlow<Task> = _task

    init {
        viewModelScope.launch {
            _task.value = taskRepository.getTaskById(id)!!
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
//            taskRepository.updateTask(task)
        }
    }

    fun bookmarkTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.bookmarkTask(taskId)
        }
    }
}