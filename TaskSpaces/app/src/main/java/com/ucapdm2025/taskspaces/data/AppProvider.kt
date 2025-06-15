package com.ucapdm2025.taskspaces.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ucapdm2025.taskspaces.data.database.AppDatabase
import com.ucapdm2025.taskspaces.data.remote.RetrofitInstance
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
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
    private val workspaceService = RetrofitInstance.workspaceService
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl(workspaceDao, workspaceService)

    fun provideWorkspaceRepository(): WorkspaceRepository {
        return workspaceRepository
    }

    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }
}