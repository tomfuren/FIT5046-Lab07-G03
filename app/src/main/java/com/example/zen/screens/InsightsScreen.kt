package com.example.zen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zen.ui.theme.ZenTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap

/**
 * Insights screen (Assessment 2 prototype).
 *
 * Report alignment:
 * - 7-day mood trend: line chart (Canvas)
 * - 30-day / monthly stress: bar chart (simple bars)
 * - Weekly/monthly toggle
 * - A small summary card with averages
 *
 * Charts are intentionally simple and use stub values for the prototype.
 * In Assessment 4, these values will be derived from stored mood entries.
 */
@Composable
fun InsightsScreen(onViewHistory: () -> Unit = {}) {
    // TODO: Assessment 4
    var selectedRange by remember { mutableStateOf("Week") }

    val weekMoodValues = listOf(3, 5, 4, 6, 7, 5, 8)
    val weekLabels = listOf("M", "T", "W", "T", "F", "S", "S")

    val monthMoodValues = listOf(6, 5, 7, 6)
    val monthMoodLabels = listOf("W1", "W2", "W3", "W4")

    val (moodValues, labels) = if (selectedRange == "Week") {
        weekMoodValues to weekLabels
    } else {
        monthMoodValues to monthMoodLabels
    }

    val stressValues = if (selectedRange == "Week") {
        // Stub: daily stress levels
        listOf(2, 3, 2, 4, 3, 2, 5)
    } else {
        // Stub: weekly stress summary
        listOf(3, 4, 3, 2)
    }

    val moodAverage = remember(moodValues) { moodValues.average() }
    val stressAverage = remember(stressValues) { stressValues.average() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Insights",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(12.dp))

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .selectableGroup()
        ) {
            listOf("Week", "Month").forEachIndexed { index, option ->
                SegmentedButton(
                    selected = selectedRange == option,
                    onClick = { selectedRange = option },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = 2),
                    label = { Text(option) }
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = if (selectedRange == "Week") "7-day mood trend" else "Monthly mood summary",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Using Compose's foundation Canvas API to draw the 7-day mood trend
        // line chart. Offset math is derived from the data range so the polyline
        // always fits the available width/height regardless of value magnitude.
        Column {
            val chartBg = MaterialTheme.colorScheme.surfaceVariant
            val lineColor = MaterialTheme.colorScheme.primary
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(chartBg)
                    .padding(12.dp)
            ) {
                val values = moodValues.map { it.toFloat() }
                if (values.size >= 2) {
                    // Normalise the data so the line fills the canvas vertically:
                    // min maps to the bottom edge, max to the top. The
                    // `takeIf { it > 0f } ?: 1f` guard protects against a flat
                    // series (all identical values) to avoid divide-by-zero.
                    val maxV = values.maxOrNull() ?: 1f
                    val minV = values.minOrNull() ?: 0f
                    val range = (maxV - minV).takeIf { it > 0f } ?: 1f

                    // stepX spaces the samples evenly across the canvas width:
                    // N points need N-1 gaps. The y coordinate is inverted
                    // (size.height - ...) because Compose Canvas's origin is
                    // top-left with y growing downward, while a "higher" mood
                    // value should visually render higher up the chart.
                    val stepX = size.width / (values.size - 1)
                    val points = values.mapIndexed { i, v ->
                        val x = stepX * i
                        val y = size.height - ((v - minV) / range) * size.height
                        Offset(x, y)
                    }

                    // Two-pass draw: line segments first, then a circle at each
                    // sample. This ordering keeps the dots on top so they stay
                    // visible where the line crosses them. Separating the loops
                    // also lets the stroke style (round cap) differ from the
                    // circle style (filled disc).
                    for (i in 0 until points.size - 1) {
                        drawLine(
                            color = lineColor,
                            start = points[i],
                            end = points[i + 1],
                            strokeWidth = 6f,
                            cap = StrokeCap.Round
                        )
                    }
                    points.forEach { p ->
                        drawCircle(
                            color = lineColor,
                            radius = 7f,
                            center = p
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                labels.forEach { label ->
                    Text(text = label, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = if (selectedRange == "Week") "7-day stress trend" else "Monthly stress summary",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            stressValues.forEachIndexed { index, value ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Stub mapping: multiplying the 1-5 stress value by 22dp
                    // keeps the tallest bar near the 160dp chart height. In
                    // Assessment 4 this will be replaced with a proportional
                    // mapping against the real max stress value from Room.
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height((value * 22).dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = labels.getOrElse(index) { "" },
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Weekly summary",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Average mood: ${"%.1f".format(moodAverage)} / 10",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Average stress: ${"%.1f".format(stressAverage)} / 5",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onViewHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View full history")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsightsScreenPreview() {
    ZenTheme {
        InsightsScreen()
    }
}