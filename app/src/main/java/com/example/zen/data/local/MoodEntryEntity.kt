package com.example.zen.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity (Assessment 2 skeleton).
 *
 * Represents a mood log entry that will be persisted locally in Assessment 4.
 */
@Suppress("unused")
@Entity(tableName = "mood_entries")
data class MoodEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val emotion: String,
    val stress: String,
    val journal: String
)

