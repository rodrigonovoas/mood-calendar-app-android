package com.rodrigonovoa.moodcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moods = listOf<String>("Happy", "Sad", "Happy", "Sad", "Happy", "Sad", "Happy", "Sad")
        val adapter = CalendarAdapter(moods)
        val rcMoods = findViewById<RecyclerView>(R.id.rc_moods)
        rcMoods.layoutManager = GridLayoutManager(this@MainActivity, 7)
        rcMoods.adapter = adapter
    }
}