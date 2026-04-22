package com.example.zen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MoodEntryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ZenDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao
}

