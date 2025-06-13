package com.ucapdm2025.taskspaces.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ucapdm2025.taskspaces.data.remote.services.auth.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

class AuthRepository(
    private val dataStore: DataStore<Preferences>,
    private val authService: AuthService
) {
    private companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    val authToken: Flow<String> = dataStore.data.catch { error ->
        if (error is IOException) {
            emit(emptyPreferences())

        } else {
            throw error
        }
    }
        .map { preferences ->
            preferences[AUTH_TOKEN] ?: ""
        }

    suspend fun login(
        email: String,
        password: String
    ): String {
        return authService.login(email, password).content.token
    }

    suspend fun saveAuthToken(
        token: String
    ) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }
}