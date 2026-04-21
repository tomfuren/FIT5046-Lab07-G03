package com.example.zen.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen() {
    val emotions = listOf("Happy", "Calm", "Anxious", "Stressed", "Sad", "Angry")
    val stressLevels = listOf("Low", "Mild", "Moderate", "High", "Severe")

    var isEmotionExpanded by remember { mutableStateOf(false) }
    var selectedEmotion by remember { mutableStateOf("") }
    var emotionError by remember { mutableStateOf(false) }

    var selectedStress by remember { mutableStateOf("Moderate") }

    var journal by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var selectedDate by remember {
        mutableStateOf(
            formatter.format(
                Date(datePickerState.selectedDateMillis ?: Instant.now().toEpochMilli())
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("How are you feeling?", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        ExposedDropdownMenuBox(
            expanded = isEmotionExpanded,
            onExpandedChange = { isEmotionExpanded = it }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedEmotion,
                onValueChange = {},
                label = { Text("Emotion") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEmotionExpanded)
                },
                isError = emotionError,
                supportingText = if (emotionError) {
                    { Text("Please select an emotion to continue") }
                } else null
            )
            ExposedDropdownMenu(
                expanded = isEmotionExpanded,
                onDismissRequest = { isEmotionExpanded = false }
            ) {
                emotions.forEach { emotion ->
                    DropdownMenuItem(
                        text = { Text(emotion) },
                        onClick = {
                            selectedEmotion = emotion
                            isEmotionExpanded = false
                            emotionError = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        Text("Stress level", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Column {
            stressLevels.forEach { level ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedStress == level,
                        onClick = { selectedStress = level }
                    )
                    Text(level, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        Text("Journal", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = journal,
            onValueChange = { journal = it },
            label = { Text("Write your thoughts...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Spacer(Modifier.height(16.dp))

        Text("Date", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        TextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = formatter.format(Date(it))
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedEmotion.isEmpty()) {
                    emotionError = true
                } else {
                    // TODO: Assessment 4
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    ZenTheme {
        LogScreen()
    }
}
