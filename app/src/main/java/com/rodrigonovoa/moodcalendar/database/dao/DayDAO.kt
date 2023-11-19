package com.rodrigonovoa.moodcalendar.database.dao

import androidx.room.*
import com.rodrigonovoa.moodcalendar.database.data.DayEntity
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity

@Dao
interface DayDAO {
    @Insert
    fun insertMoodDay(moodDay: DayEntity): Long

    @Query("select * from Day")
    fun getAllMoodDays(): List<DayEntity>

    @Query("SELECT Day.dayId AS id, Mood.id AS moodId, Day.day FROM Mood INNER JOIN Day ON Mood.id = Day.moodId WHERE Day.month = :month")
    fun getMoodWithDayByMonth(month: Int): List<MoodWithDayEntity>

    @Query("DELETE FROM Day WHERE dayId = :id")
    fun deleteMoodDayById(id: Int): Int

    @Update
    fun updateMoodDay(moodDay: DayEntity)

    @Delete
    fun deleteMoodDay(moodDay: DayEntity)
}