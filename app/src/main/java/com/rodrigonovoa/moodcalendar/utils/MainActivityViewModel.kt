package com.rodrigonovoa.moodcalendar.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val database: MoodDatabase): ViewModel() {

    fun getMoods() {
        val moods = database.moodDao().getAllMoods()
        Log.d("MainActivityViewModel","INSERTED MOOD $moods")
    }

    fun insertMood() {
        viewModelScope.launch(Dispatchers.IO) {
            val insertedMood = database.moodDao().insertMood(MoodEntity(1, "Testing"))
            Log.d("MainActivityViewModel","INSERTED MOOD $insertedMood")

            getMoods()
        }
    }

}