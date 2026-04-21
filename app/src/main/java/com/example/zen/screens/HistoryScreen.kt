package com.example.zen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme

private data class MoodEntry(
    val date: String,
    val emotion: String,
    val stress: String,
    val journalExcerpt: String
)

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(entries.size) { index ->
                    val entry = entries[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "${entry.date} · ${entry.emotion} · ${entry.stress}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = entry.journalExcerpt,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider()
                }
            }
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
