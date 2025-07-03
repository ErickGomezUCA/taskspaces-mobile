package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.WorkspaceResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toDomain

/**
 * SearchResponse is a data class that represents the response from the API for a search operation.
 *
 * @property workspaces A list of workspaces matching the search criteria.
 * @property projects A list of projects matching the search criteria.
 * @property tasks A list of tasks matching the search criteria.
 */
data class SearchResponse (
    val workspaces: List<WorkspaceResponse> = emptyList(),
    val projects: List<ProjectResponse> = emptyList(),
    val tasks: List<TaskResponse> = emptyList()
)

/**
 * Converts SearchResponse to SearchModel for application use.
 *
 * @return SearchModel representing the search results in the application.
 */
fun SearchResponse.toDomain(): SearchModel {
    return SearchModel(
        workspaces = workspaces.map { it.toDomain() },
        projects = projects.map { it.toDomain() },
        tasks = tasks.map { it.toDomain() }
    )
}