package com.ucapdm2025.taskspaces.data.repository.auth

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.remote.helpers.TokenHolder
import com.ucapdm2025.taskspaces.data.remote.requests.LoginRequest
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
    ): Result<String> {
        val request = LoginRequest(email, password)

        Log.d("test1", "request: ${request.email}, ${request.password}")

        return try {
            val response = authService.login(request)
            Result.success(response.content.token)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException){
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveAuthToken(
        token: String
    ) {
        TokenHolder.token = token // Update the global holder

        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }
}