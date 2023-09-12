package com.rodrigonovoa.moodcalendar.database.dao

import androidx.room.*
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity

@Dao
interface MoodDAO {
    @Insert
    fun insertMood(mood: MoodEntity): Long

    @Query("select * from MoodEntity")
    fun getAllMoods(): List<MoodEntity>

    @Update
    fun updateMood(mood: MoodEntity)

    @Delete
    fun deleteMood(mood: MoodEntity)
}