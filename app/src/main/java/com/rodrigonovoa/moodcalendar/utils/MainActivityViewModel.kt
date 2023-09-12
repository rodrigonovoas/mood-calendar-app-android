package com.rodrigonovoa.moodcalendar.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodDayEntity
import com.rodrigonovoa.moodcalendar.database.data.MoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val database: MoodDatabase): ViewModel() {

    private val _moods = MutableLiveData<List<MoodDayEntity>>().apply { postValue(listOf()) }
    val moods: LiveData<List<MoodDayEntity>> get() = _moods

    init {
        getMoods()
        getMoodDays()
    }
    private fun getMoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val moods = database.moodDao().getAllMoods()
            Log.d("MainActivityViewModel", "RETRIEVED MOOD $moods")
        }
    }

    private fun getMoodDays() {
        viewModelScope.launch(Dispatchers.IO) {
            val moods = database.moodDayDao().getAllMoodDays()
            _moods.postValue(moods)
            Log.d("MainActivityViewModel", "RETRIEVED MOOD DAYS$moods")
        }
    }

    fun insertMoodDayEntity(month: Int, day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val insertedMood = database.moodDayDao().insertMoodDay(MoodDayEntity(null, month, day, 1))
            Log.d("MainActivityViewModel","INSERTED MOOD DAY $insertedMood")
        }
    }

}