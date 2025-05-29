package com.ucapdm2025.taskspaces.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: Set id when initialize the ViewModel
class ProjectViewModel: ViewModel() {
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val id = 1;
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _project = MutableStateFlow<Project>(
        Project(
            id = 0,
            title = "",
            icon = "",
            createdAt = "",
            updatedAt = ""
        )
    )
    val project: StateFlow<Project> = _project

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            _project.value = projectRepository.getProjectById(id)!!
        }

        viewModelScope.launch {
            taskRepository.getTasks().collect {list ->
                _tasks.value = list
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }
}