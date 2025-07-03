package com.ucapdm2025.taskspaces.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.R
import com.ucapdm2025.taskspaces.ui.navigation.LoginRoute
import com.ucapdm2025.taskspaces.ui.navigation.OnboardingRoute
import com.ucapdm2025.taskspaces.ui.navigation.SignupRoute
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import kotlinx.coroutines.launch

data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    val heading: String,
    val body: String? = null,
    val primaryButtonText: String,
    val secondaryButtonText: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboard1,
        heading = "Empower your projects with TaskSpaces",
        primaryButtonText = "Next",
        secondaryButtonText = "Skip",
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard2,
        heading = "Organize and complete tasks",
        body = "Plan your tasks and make sure you meet your goals efficiently",
        primaryButtonText = "Next",
        secondaryButtonText = "Skip",
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard3,
        heading = "Collaborate with your team in real-time",
        body = "Work together with your team members, share ideas and updates",
        primaryButtonText = "Next",
        secondaryButtonText = "Skip",
    ),
    OnboardingPage(
        imageRes = R.drawable.onboard4,
        heading = "Get started with TaskSpaces",
        body = "Join us and start managing your projects effectively",
        primaryButtonText = "Sign Up",
        secondaryButtonText = "Have an account? Log In",
    )
)

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 10.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.heading,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        page.body?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(onboardingPages[page])
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { index ->
                val isSelected = pagerState.currentPage == index
                val color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        val currentPage = pagerState.currentPage
        val page = onboardingPages[currentPage]

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = {
                    if (currentPage == onboardingPages.lastIndex) {
                        navController.navigate(SignupRoute) {
                            popUpTo(OnboardingRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(page.primaryButtonText)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    if (currentPage == onboardingPages.lastIndex) {
                        navController.navigate(LoginRoute) {
                            popUpTo(OnboardingRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        // En cualquier otra página: saltar al final
                        scope.launch {
                            pagerState.scrollToPage(onboardingPages.lastIndex)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(page.secondaryButtonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardingLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            val navController = rememberNavController()
            OnboardingScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardingDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            val navController = rememberNavController()
            OnboardingScreen(navController = navController)
        }
    }
}
