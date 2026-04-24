package com.example.zen.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme

/**
 * One row in the Tips Library. Kept inside this file because it is only
 * used as sample UI data — will be replaced by a Room entity in Assessment 4.
 */
private data class Tip(
    val title: String,
    val category: String,
    val description: String
)

/**
 * Tips Library screen (Assessment 2 prototype).
 *
 * Demonstrates:
 * - Category filter chips (horizontal scroll)
 * - LazyColumn list
 * - Expandable cards (tap to expand, showing extra guidance)
 *
 * In Assessment 4, tips can be driven by context (weather/steps/stress) and persisted.
 */
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
    // Only one card expands at a time for a clean reading experience.
    var expandedTitle by remember { mutableStateOf<String?>(null) }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Using Button/OutlinedButton as a simple chip-style selector:
                // filled Button marks the selected category, OutlinedButton marks
                // the rest. Keeps the implementation aligned with widgets taught
                // in the course (Weeks 1-6).
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
                // Week 3-4 LazyColumn pattern: items(size) { index -> } reads
                // each element by index. The list-argument overload items(list)
                // is outside the course scope so is avoided intentionally.
                items(filteredTips.size) { index ->
                    val tip = filteredTips[index]
                    val expanded = expandedTitle == tip.title
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                expandedTitle = if (expanded) null else tip.title
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = tip.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = tip.category,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        expandedTitle = if (expanded) null else tip.title
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = "Expand tip"
                                    )
                                }
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = tip.description,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = if (expanded) Int.MAX_VALUE else 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (expanded) {
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "How to",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Set a 1-minute timer. Focus on slow, steady breaths. If your mind wanders, gently return attention to the count.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "Why it helps",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Controlled breathing can reduce physiological arousal and support emotional regulation during stress.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
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