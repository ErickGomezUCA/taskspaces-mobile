package com.ucapdm2025.taskspaces.data.repository.auth

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.helpers.TokenHolder
import com.ucapdm2025.taskspaces.data.remote.requests.auth.LoginRequest
import com.ucapdm2025.taskspaces.data.remote.services.auth.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

/** * AuthRepository is responsible for handling authentication-related operations,
 * such as logging in and managing the authentication token.
 *
 * @property dataStore The DataStore instance for storing preferences.
 * @property authService The AuthService instance for making network requests related to authentication.
 */
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

        return try {
            val response = authService.login(request)
            Result.success(response.content.token)
        } catch (e: HttpException) {
            Log.e("AuthRepository", "Login failed: ${e.message}", e)
            Result.failure(e)
        } catch (e: IOException){
            Log.e("AuthRepository", "Network error during login: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Unexpected error during login: ${e.message}", e)
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