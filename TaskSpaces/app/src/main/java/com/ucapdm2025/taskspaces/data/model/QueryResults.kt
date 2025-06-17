package com.ucapdm2025.taskspaces.data.model

/**
 * QueryResults is a data class that encapsulates the results of a query operation.
 * It can hold lists of various model types such as WorkspaceModel, ProjectModel,
 * TaskModel, and UserModel. This class is used to return multiple types of results
 * from a single query operation.
 *
 * @property workspaceModels A list of WorkspaceModel objects, or null if not applicable.
 * @property projectModels A list of ProjectModel objects, or null if not applicable.
 * @property taskModels A list of TaskModel objects, or null if not applicable.
 * @property userModels A list of UserModel objects, or null if not applicable.
 */
data class QueryResults(
    val workspaceModels: List<WorkspaceModel>? = null,
    val projectModels: List<ProjectModel>? = null,
    val taskModels: List<TaskModel>? = null,
    val userModels: List<UserModel>? = null
) {
    fun isEmpty(): Boolean {
        return workspaceModels.isNullOrEmpty() &&
                projectModels.isNullOrEmpty() &&
                taskModels.isNullOrEmpty() &&
                userModels.isNullOrEmpty()
    }
}