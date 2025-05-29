package com.ucapdm2025.taskspaces.data.model

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