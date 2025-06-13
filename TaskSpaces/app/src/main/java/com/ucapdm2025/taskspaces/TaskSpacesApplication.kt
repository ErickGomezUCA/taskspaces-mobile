package com.ucapdm2025.taskspaces

import android.app.Application
import com.ucapdm2025.taskspaces.data.AppProvider

class TaskSpacesApplication: Application() {
    val appProvider by lazy {
        AppProvider(this)
    }
}