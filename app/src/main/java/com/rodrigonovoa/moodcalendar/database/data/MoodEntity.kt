package com.rodrigonovoa.moodcalendar.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoodEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String
)