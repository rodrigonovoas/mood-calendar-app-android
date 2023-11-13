package com.rodrigonovoa.moodcalendar.ui.mainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.moodcalendar.R
import com.rodrigonovoa.moodcalendar.data.CalendarMood
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity
import com.rodrigonovoa.moodcalendar.databinding.ActivityMainBinding
import com.rodrigonovoa.moodcalendar.ui.moodPopup.MoodPopupManager
import com.rodrigonovoa.moodcalendar.ui.moodPopup.MoodPopupManagerInterface
import com.rodrigonovoa.moodcalendar.utils.CalendarUtils
import java.text.DateFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity(), MoodPopupManagerInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var moodPopupManager: MoodPopupManager
    private val COLUMNS_NUMBER = 7
    private var currentMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MoodDatabase.getInstance(this@MainActivity)
        viewModel = MainActivityViewModel(database)
        moodPopupManager = MoodPopupManager(this, this)

        observers()
        getCurrentMonth()
        setViewListeners()
        getCurrentMonthMoods()
    }

    private fun getCurrentMonthMoods() {
        viewModel.getMoodDays(currentMonth)
    }

    private fun setViewListeners() {
        binding.imvPreviousMonth.setOnClickListener {
            currentMonth = currentMonth - 1
            binding.tvMonth.text =
                DateFormatSymbols.getInstance(Locale.ENGLISH).months.get(currentMonth - 1)
            viewModel.getMoodDays(currentMonth)
        }
        binding.imvNextMonth.setOnClickListener {
            currentMonth = currentMonth + 1
            if (currentMonth == 13) { currentMonth = 1 }
            binding.tvMonth.text =
                DateFormatSymbols.getInstance(Locale.ENGLISH).months.get(currentMonth - 1)
            viewModel.getMoodDays(currentMonth)
        }
    }

    private fun observers() {
        viewModel.moods.observe(this) { moods ->
            setCalendarRecyclerView(moods)
        }

        viewModel.moodInserted.observe(this) { inserted ->
            if (inserted) { viewModel.getMoodDays(currentMonth) }
        }
    }

    private fun setCalendarRecyclerView(moods: List<MoodWithDayEntity>) {
        val moods = mapToMoodDayEntity(moods)
        val adapter = CalendarAdapter(getMoodsInCalendarFormat(moods))
        val rcMoods = findViewById<RecyclerView>(R.id.rc_moods)

        rcMoods.layoutManager = GridLayoutManager(this@MainActivity, COLUMNS_NUMBER)
        rcMoods.adapter = adapter

        adapter.onMoodClick = {
            moodPopupManager.openMoodPopup(it)
        }
    }

    private fun mapToMoodDayEntity(moods: List<MoodWithDayEntity>): List<CalendarMood> {
        return moods.map { CalendarMood(it.day, it.moodId) }
    }

    private fun getMoodsInCalendarFormat(userMoods: List<CalendarMood>): List<Int> {
        val daysOfTheMonth = CalendarUtils.getCurrentMonthDays()

        val userMoodsMapped = (1..daysOfTheMonth).map { day ->
            userMoods.find { s -> s.day == day }?.moodId ?: 0
        }

        return userMoodsMapped
    }

    private fun getCurrentMonth() {
        // from Calendar, month 0 is January
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        binding.tvMonth.text =
            DateFormatSymbols.getInstance(Locale.ENGLISH).months.get(currentMonth - 1)
    }

    override fun insertMood(day: Int, moodType: Int) {
        viewModel.insertMoodDayEntity(currentMonth, day, moodType)
    }
}