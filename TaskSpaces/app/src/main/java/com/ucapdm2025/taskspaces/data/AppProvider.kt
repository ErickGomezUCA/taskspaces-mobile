package com.ucapdm2025.taskspaces.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ucapdm2025.taskspaces.data.database.AppDatabase
import com.ucapdm2025.taskspaces.data.remote.RetrofitInstance
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl

/**
 * AppProvider is a class that provides instances of repositories and services used in the application.
 * It initializes the database, DAOs, and remote services, and provides methods to access the repositories.
 *
 * @property context The application context used to initialize the database and data store.
 */
private const val AUTH_TOKEN_NAME = "auth_token"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_TOKEN_NAME)

class AppProvider(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
//    Users
    private val userDao = appDatabase.userDao()
    private val userService = RetrofitInstance.userService
    private val userRepository: UserRepository = UserRepositoryImpl(userDao, userService)

//    Auth
    private val authService = RetrofitInstance.authService
    private val authRepository: AuthRepository = AuthRepository(context.dataStore, authService)

//    Workspace
    private val workspaceDao = appDatabase.workspaceDao()
    private val workspaceMemberDao = appDatabase.workspaceMemberDao()
    private val workspaceService = RetrofitInstance.workspaceService
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl(workspaceDao, workspaceMemberDao, workspaceService)

//    Project
    private val projectDao = appDatabase.projectDao()
    private val projectService = RetrofitInstance.projectService
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl(projectDao, projectService)

//    Task
    private val taskDao = appDatabase.taskDao()
    private val taskService = RetrofitInstance.taskService
    private val taskRepository: TaskRepository = TaskRepositoryImpl(taskDao, taskService)

//    Bookmark
    private val bookmarkDao = appDatabase.bookmarkDao()
    private val bookmarkService = RetrofitInstance.bookmarkService
    private val bookmarkRepository: BookmarkRepository = BookmarkRepositoryImpl(
        authRepository = authRepository,
        bookmarkDao = bookmarkDao,
        bookmarkService = bookmarkService,
        taskDao = taskDao
    )

    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }

    fun provideWorkspaceRepository(): WorkspaceRepository {
        return workspaceRepository
    }

    fun provideProjectRepository(): ProjectRepository {
        return projectRepository
    }

    fun provideTaskRepository(): TaskRepository {
        return taskRepository
    }

    fun provideBookmarkRepository(): BookmarkRepository {
        return bookmarkRepository
    }
}