package com.rodrigonovoa.moodcalendar.ui.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.DayEntity
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val database: MoodDatabase): ViewModel() {

    private val _moods = MutableLiveData<List<MoodWithDayEntity>>().apply { postValue(listOf()) }
    val moods: LiveData<List<MoodWithDayEntity>> get() = _moods

    fun getMoodDays(month: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val moods = database.moodDayDao().getMoodWithDayByMonth(month)
            _moods.postValue(moods)
        }
    }

    fun insertMoodDayEntity(month: Int, day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            database.moodDayDao().insertMoodDay(DayEntity(null, month, day, 1))
        }
    }

}