package com.ucapdm2025.taskspaces.data.model

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