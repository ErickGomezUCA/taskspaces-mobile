package com.ucapdm2025.taskspaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ucapdm2025.taskspaces.ui.layout.AppScaffold
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskSpacesTheme {
                ExtendedColors {
//                OnboardingNavigation provides the navigation from onboarding screen, then
//                to login or signup, and finally to the main app screen

//                To avoid that navigation and start straight into the main app navigation,
//                just uncomment AppScaffold and comment the OnboardingNavigation

//                    OnboardingNavigation()
                    AppScaffold()
                }
            }
        }
    }
}
