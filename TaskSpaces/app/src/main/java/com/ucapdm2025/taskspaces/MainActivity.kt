package com.ucapdm2025.taskspaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ucapdm2025.taskspaces.ui.layout.AppScaffold
import com.ucapdm2025.taskspaces.ui.navigation.OnboardingNavigation
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskSpacesTheme {
                OnboardingNavigation()
//                AppScaffold()
            }
        }
    }
}
