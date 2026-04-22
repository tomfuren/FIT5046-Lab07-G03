package com.example.zen.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
/**
 * Profile / Settings screen (Assessment 2 prototype).
 *
 * Demonstrates:
 * - Preference selection (RadioButton)
 * - Reminder switch + TimePicker UI
 * - Dark mode switch wired to the actual app theme (so it is visible in screenshots)
 *
 * In Assessment 4, these settings will be persisted (Room/Firebase) and used for notifications.
 */
fun ProfileScreen(
    onLogout: () -> Unit = {},
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val tipCategories = listOf("Breathing", "Movement", "Journaling", "Mixed")

    var selectedCategory by remember { mutableStateOf("Mixed") }
    var reminderEnabled by remember { mutableStateOf(true) }
    // Prototype default reminder time.
    var reminderTime by remember { mutableStateOf("08:00") }
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = reminderTime.substringBefore(":").toIntOrNull() ?: 8,
        initialMinute = reminderTime.substringAfter(":").toIntOrNull() ?: 0,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        Text(
            // TODO: Assessment 4
            text = "Alex",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            // TODO: Assessment 4
            text = "alex@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                // TODO: Assessment 4
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit profile")
        }
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tip category",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Column {
            tipCategories.forEach { category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                    Text(category, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Reminders",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Daily reminder",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = reminderEnabled,
                onCheckedChange = { reminderEnabled = it }
            )
        }
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = reminderEnabled) { showTimePicker = true }
        ) {
            TextField(
                // TODO: Assessment 4
                value = reminderTime,
                onValueChange = {},
                readOnly = true,
                label = { Text("Reminder time") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Reminder time"
                    )
                },
                supportingText = if (reminderEnabled) {
                    { Text("Tap to change time") }
                } else null,
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        if (reminderEnabled) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Set a consistent time to build the check-in habit.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Appearance",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dark mode",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            // TODO: Assessment 4
            Switch(
                checked = darkMode,
                onCheckedChange = onDarkModeChange
            )
        }
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Choose reminder time") },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                Button(
                    onClick = {
                        reminderTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                        showTimePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ZenTheme {
        ProfileScreen(
            darkMode = false,
            onDarkModeChange = {}
        )
    }
}
