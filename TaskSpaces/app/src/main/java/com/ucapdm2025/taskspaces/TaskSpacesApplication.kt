package com.ucapdm2025.taskspaces

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ucapdm2025.taskspaces.data.AppProvider
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository

private const val AUTH_TOKEN_NAME = "auth_token"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_TOKEN_NAME)

class TaskSpacesApplication: Application() {
    lateinit var authRepository: AuthRepository

    val appProvider by lazy {
        AppProvider(this)
    }

    override fun onCreate() {
        super.onCreate()

        authRepository = AuthRepository(dataStore)

        val workspaceRepository = appProvider.provideWorkspaceRepository()
        val userRepository = appProvider.provideUserRepository()
    }
}