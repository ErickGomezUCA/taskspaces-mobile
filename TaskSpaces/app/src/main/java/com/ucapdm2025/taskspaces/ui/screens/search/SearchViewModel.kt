package com.ucapdm2025.taskspaces.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.User
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

data class QueryResults(
    val workspaces: List<Workspace>? = null,
    val projects: List<Project>? = null,
    val tasks: List<Task>? = null,
    val users: List<User>? = null
) {
    fun isEmpty(): Boolean {
        return workspaces.isNullOrEmpty() &&
                projects.isNullOrEmpty() &&
                tasks.isNullOrEmpty() &&
                users.isNullOrEmpty()
    }
}

class SearchViewModel: ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()
    private val userRepository: UserRepository = UserRepositoryImpl()

    private val workspaces = workspaceRepository.getWorkspaces().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val projects = projectRepository.getProjects().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val tasks = taskRepository.getTasks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val users = userRepository.getUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _results =  MutableStateFlow(QueryResults())
    val results: StateFlow<QueryResults> = _results.asStateFlow()

    fun searchResults(query: String) {
        // TODO: Implement search logic here

        _results.value = QueryResults(
            workspaces = workspaces.value,
            projects = projects.value,
            tasks = tasks.value,
            users = users.value
        )
    }
}