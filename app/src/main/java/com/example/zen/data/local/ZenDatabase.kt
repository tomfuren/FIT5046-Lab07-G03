package com.example.zen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database definition (Assessment 2 skeleton).
 *
 * This is included to demonstrate the required Room component.
 * It will be instantiated and used in Assessment 4.
 */
@Suppress("unused")
@Database(
    entities = [MoodEntryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ZenDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao
}

