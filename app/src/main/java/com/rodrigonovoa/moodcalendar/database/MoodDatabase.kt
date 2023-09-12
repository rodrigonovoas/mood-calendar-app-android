package com.rodrigonovoa.moodcalendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodrigonovoa.moodcalendar.database.dao.MoodDAO
import com.rodrigonovoa.moodcalendar.database.dao.MoodDayDAO
import com.rodrigonovoa.moodcalendar.database.data.MoodDayEntity
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity

@Database(entities = [MoodDayEntity::class, MoodEntity::class], version = 1)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDAO
    abstract fun moodDayDao(): MoodDayDAO

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