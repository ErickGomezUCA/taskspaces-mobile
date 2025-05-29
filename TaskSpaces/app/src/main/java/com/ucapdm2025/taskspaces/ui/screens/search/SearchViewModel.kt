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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

class SearchViewModel : ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()
    private val userRepository: UserRepository = UserRepositoryImpl()

    //    Results
    private val _results = MutableStateFlow(QueryResults())
    val results: StateFlow<QueryResults> = _results.asStateFlow()

    private val _workspacesResults = MutableStateFlow<List<Workspace>>(emptyList())
    val workspacesResults: StateFlow<List<Workspace>> = _workspacesResults.asStateFlow()

    private val _projectsResults = MutableStateFlow<List<Project>>(emptyList())
    val projectsResults: StateFlow<List<Project>> = _projectsResults.asStateFlow()

    private val _tasksResults = MutableStateFlow<List<Task>>(emptyList())
    val tasksResults: StateFlow<List<Task>> = _tasksResults.asStateFlow()

    private val _usersResults = MutableStateFlow<List<User>>(emptyList())
    val usersResults: StateFlow<List<User>> = _usersResults.asStateFlow()

    //    Data from repositories
    private val _workspaces = MutableStateFlow<List<Workspace>>(emptyList())
    private val workspaces: StateFlow<List<Workspace>> = _workspaces

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    private val projects: StateFlow<List<Project>> = _projects

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val tasks: StateFlow<List<Task>> = _tasks

    private val _users = MutableStateFlow<List<User>>(emptyList())
    private val users: StateFlow<List<User>> = _users

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspaces().collect { list ->
                _workspaces.value = list
            }
        }

        viewModelScope.launch {
            projectRepository.getProjects().collect { list ->
                _projects.value = list
            }
        }

        viewModelScope.launch {
            taskRepository.getTasks().collect { list ->
                _tasks.value = list
            }
        }

        viewModelScope.launch {
            userRepository.getUsers().collect { list ->
                _users.value = list
            }
        }
    }

    fun searchWorkspaces(query: String) {
        val filteredWorkspaces = workspaces.value.filter { workspace ->
            workspace.title.contains(query, ignoreCase = true)
        }
        _workspacesResults.value = filteredWorkspaces
    }

    fun searchProjects(query: String) {
        val filteredProjects = projects.value.filter { project ->
            project.title.contains(query, ignoreCase = true)
        }
        _projectsResults.value = filteredProjects
    }

    fun searchTasks(query: String) {
        val filteredTasks = tasks.value.filter { task ->
            task.title.contains(query, ignoreCase = true) ||
                    task.description.contains(query, ignoreCase = true)
        }
        _tasksResults.value = filteredTasks
    }

    fun searchUsers(query: String) {
        val filteredUsers = users.value.filter { user ->
            user.fullname.contains(query, ignoreCase = true) ||
                    user.username.contains(query, ignoreCase = true)
            user.email.contains(query, ignoreCase = true)
        }
        _usersResults.value = filteredUsers
    }

    fun searchResults(query: String) {
        // TODO: Implement search logic here
        searchWorkspaces(query)
        searchProjects(query)
        searchTasks(query)
        searchUsers(query)

        _results.value = QueryResults(
            workspaces = _workspacesResults.value,
            projects = _projectsResults.value,
            tasks = _tasksResults.value,
            users = _usersResults.value
        )
    }
}