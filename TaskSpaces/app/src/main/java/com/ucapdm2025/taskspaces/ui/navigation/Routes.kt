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
// taskId is optional, used for navigating and opening a task dialog within a project
data class ProjectRoute(val projectId: Int, val taskId : Int? = null)

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