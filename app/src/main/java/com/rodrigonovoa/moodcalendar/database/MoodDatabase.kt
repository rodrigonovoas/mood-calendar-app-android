package com.rodrigonovoa.moodcalendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodrigonovoa.moodcalendar.database.dao.MoodDAO
import com.rodrigonovoa.moodcalendar.database.dao.DayDAO
import com.rodrigonovoa.moodcalendar.database.data.DayEntity
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity

@Database(entities = [DayEntity::class, MoodEntity::class], version = 3)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDAO
    abstract fun moodDayDao(): DayDAO

    companion object {
        private var instance: MoodDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MoodDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, MoodDatabase::class.java,
                    "mood_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }

    }

}