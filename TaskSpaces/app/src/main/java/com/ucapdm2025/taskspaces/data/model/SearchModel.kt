package com.ucapdm2025.taskspaces.data.model

/**
 * SearchResultModel is a data class that represents the result of a search operation.
 *
 * @property workspaces A list of workspaces that match the search criteria.
 * @property projects A list of projects that match the search criteria.
 * @property tasks A list of tasks that match the search criteria.
 */
data class SearchModel(
    val workspaces: List<WorkspaceModel> = emptyList<WorkspaceModel>(),
    val projects: List<ProjectModel> = emptyList<ProjectModel>(),
    val tasks: List<TaskModel> = emptyList<TaskModel>()
)