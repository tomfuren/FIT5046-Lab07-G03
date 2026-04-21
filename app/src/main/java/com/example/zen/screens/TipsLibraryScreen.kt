package com.example.zen.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

private data class Tip(
    val title: String,
    val category: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsLibraryScreen(onBack: () -> Unit = {}) {
    // TODO: Assessment 4
    val tips = listOf(
        Tip("Box Breathing", "Breathing", "Inhale 4s, hold 4s, exhale 4s, hold 4s. Repeat 4 cycles."),
        Tip("4-7-8 Breathing", "Breathing", "Inhale 4s, hold 7s, exhale 8s. Calms the nervous system."),
        Tip("Belly Breathing", "Breathing", "Place hand on belly, breathe deeply so hand rises."),
        Tip("5-Minute Walk", "Movement", "Short walk resets focus and reduces tension."),
        Tip("Desk Stretches", "Movement", "Neck rolls, shoulder shrugs, and wrist circles at your desk."),
        Tip("Stand and Reach", "Movement", "Stand up, reach overhead, and stretch for 30 seconds."),
        Tip("Gratitude List", "Journaling", "Write 3 things you're grateful for today."),
        Tip("Mood Journal", "Journaling", "Describe how you feel right now in 2-3 sentences."),
        Tip("Worry Dump", "Journaling", "Write down all your worries to clear mental space."),
        Tip("Body Scan", "Mindfulness", "Notice sensations from head to toe without judgment."),
        Tip("5-4-3-2-1 Grounding", "Mindfulness", "Name 5 things you see, 4 hear, 3 touch, 2 smell, 1 taste."),
        Tip("Mindful Sip", "Mindfulness", "Focus fully on one sip of water or tea.")
    )

    val categories = listOf("All", "Breathing", "Movement", "Journaling", "Mindfulness")

    var selectedCategory by remember { mutableStateOf("All") }

    val filteredTips = if (selectedCategory == "All") {
        tips
    } else {
        tips.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tips Library") },
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    if (selectedCategory == category) {
                        Button(onClick = { selectedCategory = category }) {
                            Text(category)
                        }
                    } else {
                        OutlinedButton(onClick = { selectedCategory = category }) {
                            Text(category)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTips.size) { index ->
                    val tip = filteredTips[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = tip.title,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = tip.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = tip.description,
                            style = MaterialTheme.typography.bodyMedium
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
fun TipsLibraryScreenPreview() {
    ZenTheme {
        TipsLibraryScreen()
    }
}
