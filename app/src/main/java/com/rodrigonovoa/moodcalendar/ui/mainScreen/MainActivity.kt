package com.rodrigonovoa.moodcalendar.ui.mainScreen

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.moodcalendar.R
import com.rodrigonovoa.moodcalendar.data.CalendarMood
import com.rodrigonovoa.moodcalendar.database.MoodDatabase
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity
import com.rodrigonovoa.moodcalendar.databinding.ActivityMainBinding
import com.rodrigonovoa.moodcalendar.utils.CalendarUtils
import java.text.DateFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private val COLUMNS_NUMBER = 7
    private var currentMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MoodDatabase.getInstance(this@MainActivity)
        viewModel = MainActivityViewModel(database)

        observers()
        getCurrentMonth()
        setViewListeners()
        getCurrentMonthMoods()
    }

    private fun openMoodPopup(day: Int) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_mood_popup, null)
        val buttonClose = dialogLayout.findViewById<Button>(R.id.buttonClose)

        val imvHappy = dialogLayout.findViewById<ImageView>(R.id.imv_happy)
        val imvNeutral = dialogLayout.findViewById<ImageView>(R.id.imv_neutral)
        val imvSad = dialogLayout.findViewById<ImageView>(R.id.imv_sad)

        builder.setView(dialogLayout)
        val customDialog = builder.create()

        imvHappy.setOnClickListener {
            viewModel.insertMoodDayEntity(currentMonth, day, 1)
            customDialog.dismiss()
        }

        imvNeutral.setOnClickListener {
            viewModel.insertMoodDayEntity(currentMonth, day, 2)
            customDialog.dismiss()
        }

        imvSad.setOnClickListener {
            viewModel.insertMoodDayEntity(currentMonth, day,3)
            customDialog.dismiss()
        }

        buttonClose.setOnClickListener { customDialog.dismiss() }

        customDialog.show()
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
            openMoodPopup(it)
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
}