package com.rodrigonovoa.moodcalendar.database.data

import androidx.room.*

@Entity(
    tableName = "Day",
    foreignKeys = arrayOf(
        ForeignKey(entity = MoodEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("moodId"),
            onDelete = ForeignKey.CASCADE)
    ))

data class DayEntity
    (
    @PrimaryKey(autoGenerate = true) val dayId: Int?,
    @ColumnInfo val month: Int,
    @ColumnInfo val day: Int,
    @ColumnInfo val moodId: Int
)