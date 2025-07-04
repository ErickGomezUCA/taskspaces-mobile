package com.ucapdm2025.taskspaces.ui.components.task

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

/**
 * DeadlinePicker is a composable function that allows users to select a deadline for a task.
 *
 * It displays the current deadline if available, and allows users to pick a new date and time.
 *
 * @param deadline The current deadline for the task, represented as a LocalDateTime object.
 * @param onDeadlineSelected Callback function to handle the selected deadline.
 * @param modifier Optional modifier to customize the appearance and behavior of the component.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePicker(
    deadline: LocalDateTime?,
    onDeadlineSelected: (LocalDateTime?) -> Unit,
    onDeadlineClear: () -> Unit,
    modifier: Modifier = Modifier,
    hasPermission: Boolean = false
) {
    val formatter = DateTimeFormatter.ofPattern("d / MMM / yyyy - h:mm a")
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()
    val context = LocalContext.current

    val weeksLeft = deadline?.let {
        java.time.Duration.between(LocalDateTime.now(), it).toDays() / 7
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.CalendarMonth,
                contentDescription = "Deadline",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Deadline",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (deadline != null) {
            Text(
                deadline.format(formatter),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "$weeksLeft weeks left until deadline",
                fontSize = 12.sp,
                color = ExtendedTheme.colors.background50
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (hasPermission) {
            
                if (deadline != null) {
                    OutlinedButton(
                        onClick = { onDeadlineClear() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Clear")
                    }
                }

                OutlinedButton(
                    onClick = { setShowDialog(true) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (deadline != null) "Set new deadline" else "Add Deadline +")
                }
            }

        }

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { setShowDialog(false) },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = dateState.selectedDateMillis
                        if (millis != null) {
                            val date = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(millis),
                                ZoneId.systemDefault()
                            )

                            // Show TimePicker after date selection
                            val cal = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val fullDate = date.withHour(hour).withMinute(minute)
                                    onDeadlineSelected(fullDate)
                                    setShowDialog(false)
                                },
                                cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE),
                                false
                            ).show()
                        } else {
                            setShowDialog(false)
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { setShowDialog(false) }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = dateState)
            }
        }
    }
}

/**
 * Preview of DeadlinePicker with a sample deadline.
 */
@Preview(showBackground = true)
@Composable
fun DeadlinePickerPreview() {
    val sampleDeadline = LocalDateTime.now().plusDays(10)
    DeadlinePicker(
        deadline = sampleDeadline,
        onDeadlineSelected = {},
        onDeadlineClear = {}
    )
}