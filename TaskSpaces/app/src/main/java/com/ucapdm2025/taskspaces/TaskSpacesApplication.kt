package com.ucapdm2025.taskspaces

import android.app.Application
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ucapdm2025.taskspaces.data.AppProvider
import com.ucapdm2025.taskspaces.data.remote.helpers.TokenHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskSpacesApplication: Application() {
    val appProvider by lazy {
        AppProvider(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Load auth token into TokenHolder on app startup
        CoroutineScope(Dispatchers.IO).launch {
            val authTokenKey = stringPreferencesKey("auth_token")
            val token = appProvider.provideAuthRepository()
                .authToken
                .first() // Get the first (current) token value from Flow
            TokenHolder.token = token
        }
    }
}