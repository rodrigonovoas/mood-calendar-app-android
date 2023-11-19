package com.rodrigonovoa.moodcalendar.ui.moodPopup

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import com.rodrigonovoa.moodcalendar.R

class MoodPopupManager(private val context: Context, private val moodManager: MoodPopupManagerInterface) {

    fun openMoodPopup(day: Int) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.add_mood_popup, null)

        val buttonClose = dialogLayout.findViewById<Button>(R.id.buttonClose)

        val imvHappy = dialogLayout.findViewById<ImageView>(R.id.imv_happy)
        val imvNeutral = dialogLayout.findViewById<ImageView>(R.id.imv_neutral)
        val imvSad = dialogLayout.findViewById<ImageView>(R.id.imv_sad)

        builder.setView(dialogLayout)
        val customDialog = builder.create()

        imvHappy.setOnClickListener {
            insertMoodDayEntity(day, 1)
            customDialog.dismiss()
        }

        imvNeutral.setOnClickListener {
            insertMoodDayEntity(day, 2)
            customDialog.dismiss()
        }

        imvSad.setOnClickListener {
            insertMoodDayEntity(day, 3)
            customDialog.dismiss()
        }

        buttonClose.setOnClickListener { customDialog.dismiss() }

        customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog.show()
    }

    private fun insertMoodDayEntity(day: Int, moodType: Int) {
        moodManager.insertMood(day,moodType)
    }
}

interface MoodPopupManagerInterface {
    fun insertMood(day: Int, moodType: Int)
}

