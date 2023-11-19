package com.rodrigonovoa.moodcalendar.ui.moodPopup

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.rodrigonovoa.moodcalendar.R
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity

class MoodPopupManager(private val context: Context, private val moodManager: MoodPopupManagerInterface) {

    fun openMoodPopup(mood: MoodWithDayEntity) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.add_mood_popup, null)

        val buttonClose = dialogLayout.findViewById<Button>(R.id.buttonClose)
        val buttonDelete = dialogLayout.findViewById<Button>(R.id.buttonDelete)

        val imvHappy = dialogLayout.findViewById<ImageView>(R.id.imv_happy)
        val imvNeutral = dialogLayout.findViewById<ImageView>(R.id.imv_neutral)
        val imvSad = dialogLayout.findViewById<ImageView>(R.id.imv_sad)

        builder.setView(dialogLayout)
        val customDialog = builder.create()

        if (mood.moodId == 0) { buttonDelete.visibility = View.GONE }

        imvHappy.setOnClickListener {
            insertMoodDayEntity(mood.day, 1)
            customDialog.dismiss()
        }

        imvNeutral.setOnClickListener {
            insertMoodDayEntity(mood.day, 2)
            customDialog.dismiss()
        }

        imvSad.setOnClickListener {
            insertMoodDayEntity(mood.day, 3)
            customDialog.dismiss()
        }

        buttonClose.setOnClickListener { customDialog.dismiss() }
        buttonDelete.setOnClickListener {
            moodManager.deleteMood(mood.id)
            customDialog.dismiss()
        }

        customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog.show()
    }

    private fun insertMoodDayEntity(day: Int, moodType: Int) {
        moodManager.insertMood(day,moodType)
    }
}

interface MoodPopupManagerInterface {
    fun insertMood(day: Int, moodType: Int)
    fun deleteMood(id: Int)
}

