package com.rodrigonovoa.moodcalendar.database.dao

import androidx.room.*
import com.rodrigonovoa.moodcalendar.database.data.MoodDayEntity

@Dao
interface MoodDayDAO {
    @Insert
    fun insertMoodDay(moodDay: MoodDayEntity): Long

    @Query("select * from MoodDay")
    fun getAllMoodDays(): List<MoodDayEntity>

    @Update
    fun updateMoodDay(moodDay: MoodDayEntity)

    @Delete
    fun deleteMoodDay(moodDay: MoodDayEntity)
}