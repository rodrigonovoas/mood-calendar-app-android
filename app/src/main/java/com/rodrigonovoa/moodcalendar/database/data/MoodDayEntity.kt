package com.rodrigonovoa.moodcalendar.database.data

import androidx.room.*

@Entity(
    tableName = "MoodDay",
    foreignKeys = arrayOf(
        ForeignKey(entity = MoodEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("moodId"),
            onDelete = ForeignKey.CASCADE)
    ))
data class MoodDayEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val month: Int,
    @ColumnInfo val day: Int,
    @ColumnInfo val moodId: Int
    )