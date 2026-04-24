package com.example.zen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * One recorded mood entry shown in the list. Dates are stored as ISO
 * yyyy-MM-dd strings so they can be compared lexically against the
 * From/To filter without parsing. Will be replaced by a Room entity
 * in Assessment 4.
 */
private data class MoodEntry(
    val date: String,
    val emotion: String,
    val stress: String,
    val journalExcerpt: String
)

/**
 * History screen (Assessment 2 prototype).
 *
 * What it demonstrates for the assignment:
 * - LazyColumn list of recorded items
 * - Search filtering (real-time)
 * - Date range filtering using two DatePickers (From/To)
 * - Tap-to-view details (dialog)
 * - Long-press actions (Edit/Delete bottom sheet)
 *
 * In Assessment 4, the list will come from Room/Firebase instead of hardcoded sample data.
 */
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBack: () -> Unit = {}) {
    // TODO: Assessment 4
    val entries = listOf(
        MoodEntry("2026-04-22", "Happy", "Low", "Felt great today after morning walk in the park."),
        MoodEntry("2026-04-21", "Calm", "Low", "Finished a book I've been reading for weeks."),
        MoodEntry("2026-04-20", "Anxious", "High", "Worried about tomorrow's presentation at work."),
        MoodEntry("2026-04-19", "Stressed", "High", "Too many deadlines piling up this week."),
        MoodEntry("2026-04-18", "Happy", "Moderate", "Had lunch with an old friend, good catch-up."),
        MoodEntry("2026-04-17", "Sad", "Moderate", "Missing family back home, feeling homesick."),
        MoodEntry("2026-04-16", "Calm", "Low", "Meditation session helped clear my head."),
        MoodEntry("2026-04-15", "Angry", "High", "Frustrated with a teammate's missed deadline."),
        MoodEntry("2026-04-14", "Happy", "Low", "Got a compliment from my manager on the report."),
        MoodEntry("2026-04-13", "Anxious", "Moderate", "Couldn't sleep well, racing thoughts."),
        MoodEntry("2026-04-12", "Calm", "Low", "Quiet Sunday, read and had tea."),
        MoodEntry("2026-04-11", "Stressed", "Severe", "Car broke down on the way to work."),
        MoodEntry("2026-04-10", "Happy", "Mild", "Tried a new recipe, turned out well."),
        MoodEntry("2026-04-09", "Sad", "Moderate", "Rainy day, low energy throughout."),
        MoodEntry("2026-04-08", "Calm", "Low", "Long walk in the evening, very peaceful.")
    )

    var searchQuery by remember { mutableStateOf("") }
    // Two nullable targets — one per surface — so tap and long-press can
    // open independently without racing. The AlertDialog (viewingEntry)
    // shows read-only details; the ModalBottomSheet (selectedEntry) exposes
    // Edit/Delete actions. Separating the states keeps each surface's
    // dismiss handler scoped to its own entry.
    var selectedEntry by remember { mutableStateOf<MoodEntry?>(null) }
    var viewingEntry by remember { mutableStateOf<MoodEntry?>(null) }

    // Week 3 DatePicker pattern: rememberDatePickerState + DatePickerDialog
    // + a read-only TextField with a trailing calendar icon. Applied twice
    // here (start + end) to give the user a From/To range filter.
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val nowMillis = remember { System.currentTimeMillis() }
    val startPickerState = rememberDatePickerState(initialSelectedDateMillis = nowMillis)
    val endPickerState = rememberDatePickerState(initialSelectedDateMillis = nowMillis)
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }
    var startDate by remember {
        mutableStateOf(formatter.format(Date(startPickerState.selectedDateMillis ?: nowMillis)))
    }
    var endDate by remember {
        mutableStateOf(formatter.format(Date(endPickerState.selectedDateMillis ?: nowMillis)))
    }

    val query = searchQuery.trim()
    val filteredEntries = remember(query, entries) {
        if (query.isEmpty()) {
            entries
        } else {
            val q = query.lowercase()
            entries.filter { entry ->
                entry.date.lowercase().contains(q) ||
                        entry.emotion.lowercase().contains(q) ||
                        entry.stress.lowercase().contains(q) ||
                        entry.journalExcerpt.lowercase().contains(q)
            }
        }
    }

    // Date-range filter relies on ISO yyyy-MM-dd strings: because the format
    // is zero-padded and fixed-width, lexical (string) comparison produces
    // the same ordering as chronological comparison. This avoids having to
    // parse each entry back to a Date on every recomposition.
    val dateFilteredEntries = remember(filteredEntries, startDate, endDate) {
        val start = startDate
        val end = endDate
        filteredEntries.filter { entry ->
            entry.date >= start && entry.date <= end
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search entries") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showStartPicker = true }
                ) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("From") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Start date"
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showEndPicker = true }
                ) {
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("To") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "End date"
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(dateFilteredEntries.size) { index ->
                    val entry = dateFilteredEntries[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            // combinedClickable wires up two gestures on the
                            // same row: a short tap opens the read-only detail
                            // dialog (viewingEntry); a long press opens the
                            // Edit/Delete bottom sheet (selectedEntry).
                            .combinedClickable(
                                onClick = { viewingEntry = entry },
                                onLongClick = { selectedEntry = entry }
                            )
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "${entry.date} · ${entry.emotion} · ${entry.stress}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = entry.journalExcerpt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }

    if (showStartPicker) {
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showStartPicker = false
                    startPickerState.selectedDateMillis?.let { startDate = formatter.format(Date(it)) }
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showStartPicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = startPickerState)
        }
    }

    if (showEndPicker) {
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showEndPicker = false
                    endPickerState.selectedDateMillis?.let { endDate = formatter.format(Date(it)) }
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showEndPicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = endPickerState)
        }
    }

    if (viewingEntry != null) {
        AlertDialog(
            onDismissRequest = { viewingEntry = null },
            title = { Text("Mood entry") },
            text = {
                Column {
                    Text(
                        text = "${viewingEntry?.date} · ${viewingEntry?.emotion} · ${viewingEntry?.stress}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = viewingEntry?.journalExcerpt.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { viewingEntry = null }) {
                    Text("Close")
                }
            }
        )
    }

    if (selectedEntry != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedEntry = null }
        ) {
            Text(
                text = "Entry actions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "${selectedEntry?.date} · ${selectedEntry?.emotion} · ${selectedEntry?.stress}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = {
                    // TODO: Assessment 4 (edit flow)
                    selectedEntry = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                Spacer(Modifier.width(8.dp))
                Text("Edit", modifier = Modifier.padding(start = 8.dp))
            }

            TextButton(
                onClick = {
                    // TODO: Assessment 4 (delete flow)
                    selectedEntry = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                Spacer(Modifier.width(8.dp))
                Text("Delete", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    ZenTheme {
        HistoryScreen()
    }
}