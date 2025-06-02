package com.ucapdm2025.taskspaces.ui.screens.test.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestProjectViewModel : ViewModel() {
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val projectId = 1

    private val _project: MutableStateFlow<Project?> = MutableStateFlow(null)
    val project: StateFlow<Project?> = _project

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect { project ->
                _project.value = project
            }
        }

        viewModelScope.launch {
            taskRepository.getTasksByProjectId(projectId).collect { tasks ->
                _tasks.value = tasks
            }
        }
    }

    // Add more fields here
    fun createTask(title: String, description: String, status: String, projectId: Int) {
        viewModelScope.launch {
            taskRepository.createTask(
                title = title,
                description = description,
                deadline = "",
                status = status,
                breadcrumb = "Workspace 1 / ${project.value?.title}",
                projectId = projectId
            )
        }
    }

    fun updateTask(id: Int, title: String, description: String, status: String, projectId: Int) {
        viewModelScope.launch {
            taskRepository.updateTask(
                id = id,
                title = title,
                description = description,
                deadline = "",
                status = status,
                breadcrumb = "Workspace 1 / ${project.value?.title}",
                projectId = projectId
            )
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(id)
        }
    }
}