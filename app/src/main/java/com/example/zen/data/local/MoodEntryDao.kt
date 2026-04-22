package com.example.zen.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoodEntryDao {
    @Query("SELECT * FROM mood_entries ORDER BY date DESC")
    suspend fun getAll(): List<MoodEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: MoodEntryEntity): Long

    @Update
    suspend fun update(entry: MoodEntryEntity)

    @Delete
    suspend fun delete(entry: MoodEntryEntity)
}

