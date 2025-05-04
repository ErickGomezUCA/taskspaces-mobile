package com.ucapdm2025.taskspaces.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object OnboardingRoute

@Serializable
object LoginRoute

@Serializable
object SignupRoute

@Serializable
object HomeRoute

@Serializable
data class WorkspaceRoute(val workspaceId: String)

@Serializable
data class ProjectRout(val projectId: String)

// TODO: See if this is correct, because tasks are handled by a dialog instead of a view
@Serializable
data class TaskRoute(val taskId: String)

@Serializable
data class TimeTrackerRoute(val taskId: String)

@Serializable
object SearchRoute

@Serializable
object BookmarksRoute

@Serializable
object UserRoute

@Serializable
object SettingsRoute

@Serializable
object ChangePasswordRoute