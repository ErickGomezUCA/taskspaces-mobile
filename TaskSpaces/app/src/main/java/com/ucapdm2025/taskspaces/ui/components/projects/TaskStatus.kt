package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.DoingStatusColor
import com.ucapdm2025.taskspaces.ui.theme.DoneStatusColor
import com.ucapdm2025.taskspaces.ui.theme.PendingStatusColor
import com.ucapdm2025.taskspaces.ui.theme.White100

@Composable
fun TaskStatus(status: StatusVariations) {
    val (label, backgroundColor) = when (status) {
        StatusVariations.PENDING -> "PENDING" to PendingStatusColor
        StatusVariations.DOING -> "DOING" to DoingStatusColor
        StatusVariations.DONE -> "DONE" to DoneStatusColor
    }
    Text(
        text = label,
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = White100,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )
}

@Preview(showBackground = true)
@Composable
fun TaskStatusPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskStatus(StatusVariations.PENDING)
        TaskStatus(StatusVariations.DOING)
        TaskStatus(StatusVariations.DONE)
    }
}
