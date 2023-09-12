package com.rodrigonovoa.moodcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.moodcalendar.data.CalendarMood
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodDayEntity
import com.rodrigonovoa.moodcalendar.utils.CalendarUtils
import com.rodrigonovoa.moodcalendar.utils.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val ROWS_COLUMNS_WEEK = 7
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

    private fun setCalendarRecyclerView(moods: List<MoodDayEntity>) {
        val moods = mapMoodDayEntity(moods)
        val adapter = CalendarAdapter(getMoodsInCalendarFormat(moods))
        val rcMoods = findViewById<RecyclerView>(R.id.rc_moods)
        rcMoods.layoutManager = GridLayoutManager(this@MainActivity, ROWS_COLUMNS_WEEK)
        rcMoods.adapter = adapter

        adapter.onMoodClick = {
            val CURRENT_MONTH = 9
            viewModel.insertMoodDayEntity(CURRENT_MONTH, it)
        }
    }

    private fun mapMoodDayEntity(moods: List<MoodDayEntity>): List<CalendarMood> {
        var calendarMoodList = mutableListOf<CalendarMood>()

        for (mood in moods) {
            calendarMoodList.add(CalendarMood(mood.day, mood.moodId.toString()))
        }

        return calendarMoodList
    }

    private fun getMoodsInCalendarFormat(userMoods: List<CalendarMood>): List<String> {
        val daysOfTheMonth = CalendarUtils.getCurrentMonthDays()
        var moods = mutableListOf<String>()

        for (day in 1..daysOfTheMonth) {
            val dayMood = userMoods.filter { s -> s.day == day }.singleOrNull()

            if (dayMood != null) {
                moods.add(dayMood.mood)
            } else {
                moods.add("")
            }
        }

        return moods.toList()
    }

    private fun moodExamples(): List<CalendarMood> {
        return listOf<CalendarMood>(
            CalendarMood(3, "Happy"),
            CalendarMood(5, "Neutral"),
            CalendarMood(12, "Happy"),
            CalendarMood(24, "S. Happy"))
    }


}