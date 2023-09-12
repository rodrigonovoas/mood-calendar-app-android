package com.rodrigonovoa.moodcalendar

import android.app.Application
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoodCalendar: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        val db = MoodDatabase.getInstance(applicationContext)

        insertBaseMoods(db)
    }

    private fun insertBaseMoods(db: MoodDatabase) {
        applicationScope.launch {
            db.moodDao().insertMood(MoodEntity(1, "Happy"))
            db.moodDao().insertMood(MoodEntity(2, "Neutral"))
            db.moodDao().insertMood(MoodEntity(3, "Sad"))
        }
    }

}