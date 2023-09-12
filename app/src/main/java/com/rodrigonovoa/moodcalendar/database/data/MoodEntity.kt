package com.rodrigonovoa.moodcalendar.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "Mood", indices = [Index(value = ["id"], unique = true)])
data class MoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val name: String
)