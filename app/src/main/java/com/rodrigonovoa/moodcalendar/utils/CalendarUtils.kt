package com.rodrigonovoa.moodcalendar.utils

import java.util.*

object CalendarUtils {
    fun getCurrentMonthDays(): Int {
        val calendar = Calendar.getInstance()
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}