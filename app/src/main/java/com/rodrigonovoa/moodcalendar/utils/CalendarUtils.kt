package com.rodrigonovoa.moodcalendar.utils

import java.util.*

object CalendarUtils {
    fun getCurrentMonthDays(month: Int): Int {
        val currentYear = getCurrentYear()

        return getLastDayOfMonth(currentYear, month)
    }

    private fun getLastDayOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}