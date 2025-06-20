package com.ucapdm2025.taskspaces.data.repository.search

import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * SearchRepository is an interface that defines the methods for managing search functionality in the application.
 */
interface SearchRepository {
    fun search(query: String): Flow<Resource<SearchModel>>
    fun searchWorkspaces(query: String): Flow<Resource<List<WorkspaceModel>>>
    fun searchProjects(query: String): Flow<Resource<List<ProjectModel>>>
    fun searchTasks(query: String): Flow<Resource<List<TaskModel>>>
}