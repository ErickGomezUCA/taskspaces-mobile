package com.ucapdm2025.taskspaces.ui.screens.test.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container

@Composable
fun TestHomeScreen(
    viewModel: TestHomeViewModel = viewModel()
) {
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "TestHomeViewModel example", fontWeight = FontWeight.Medium, fontSize = 20.sp)

        Container {
            Text(text = "- Get workspaces from user Id")

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                workspaces.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item?.id}")
                        Text(text = "title: ${item?.title}")
                        Text(text = "ownerId: ${item?.ownerId}")
                    }
                }
            }

        }
    }
}