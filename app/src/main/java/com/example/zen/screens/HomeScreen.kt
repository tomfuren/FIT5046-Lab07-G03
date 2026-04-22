package com.example.zen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

@Composable
/**
 * Home / Dashboard screen (Assessment 2 prototype).
 *
 * Demonstrates:
 * - "Tip for you" card (context-aware in Assessment 4; placeholder in prototype)
 * - Habit checklist (Checkbox)
 * - Habit progress (LinearProgressIndicator)
 * - Entry point buttons to Mood Log and Tips Library
 */
fun HomeScreen(onBrowseTips: () -> Unit = {}, onLogMood: () -> Unit = {}) {
    var drinkWater by remember { mutableStateOf(false) }
    var walk by remember { mutableStateOf(false) }
    var sleep by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            // TODO: Assessment 4
            text = "Good evening, Alex",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Tip for you",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.55f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    // TODO: Assessment 4
                    text = "It's evening, take 3 deep breaths",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Today's habits",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = drinkWater, onCheckedChange = { drinkWater = it })
            Text("Drink water", style = MaterialTheme.typography.bodyMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = walk, onCheckedChange = { walk = it })
            Text("5-min walk", style = MaterialTheme.typography.bodyMedium)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = sleep, onCheckedChange = { sleep = it })
            Text("Sleep by 11pm", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(8.dp))
        val completedCount = listOf(drinkWater, walk, sleep).count { it }
        val progress = completedCount / 3f
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$completedCount/3 habits completed",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Streak",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            // TODO: Assessment 4
            text = "7 day streak",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onLogMood,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log your mood now")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = onBrowseTips,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Browse Tips")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ZenTheme {
        HomeScreen()
    }
}
