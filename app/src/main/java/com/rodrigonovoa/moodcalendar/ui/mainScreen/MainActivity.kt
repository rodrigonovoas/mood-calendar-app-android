package com.rodrigonovoa.moodcalendar.ui.mainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.moodcalendar.R
import com.rodrigonovoa.moodcalendar.data.CalendarMood
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity
import com.rodrigonovoa.moodcalendar.utils.CalendarUtils

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val COLUMNS_NUMBER = 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = MoodDatabase.getInstance(this@MainActivity)

        viewModel = MainActivityViewModel(database)

        observers()
    }

    private fun observers() {
        viewModel.moods.observe(this) { moods ->
            setCalendarRecyclerView(moods)
        }
    }

    private fun setCalendarRecyclerView(moods: List<MoodWithDayEntity>) {
        val moods = mapToMoodDayEntity(moods)
        val adapter = CalendarAdapter(getMoodsInCalendarFormat(moods))
        val rcMoods = findViewById<RecyclerView>(R.id.rc_moods)

        rcMoods.layoutManager = GridLayoutManager(this@MainActivity, COLUMNS_NUMBER)
        rcMoods.adapter = adapter

        adapter.onMoodClick = {
            val CURRENT_MONTH = 9
            viewModel.insertMoodDayEntity(CURRENT_MONTH, it)
        }
    }

    private fun mapToMoodDayEntity(moods: List<MoodWithDayEntity>): List<CalendarMood> {
        return moods.map { CalendarMood(it.day, it.mood) }
    }

    private fun getMoodsInCalendarFormat(userMoods: List<CalendarMood>): List<String> {
        val daysOfTheMonth = CalendarUtils.getCurrentMonthDays()

        val userMoodsMapped = (1..daysOfTheMonth).map { day ->
            userMoods.find { s -> s.day == day }?.mood ?: ""
        }

        return userMoodsMapped
    }
}