package com.ucapdm2025.taskspaces.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object OnboardingRoute

@Serializable
object LoginRoute

@Serializable
object SignupRoute

@Serializable
object AppRoute

@Serializable
object HomeRoute

@Serializable
data class WorkspaceRoute(val workspaceId: Int)

@Serializable
data class ProjectRout(val projectId: Int)

// TODO: See if this is correct, because tasks are handled by a dialog instead of a view
@Serializable
data class TaskRoute(val taskId: Int)

@Serializable
data class TimeTrackerRoute(val taskId: Int)

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