package com.sampleproject.utils.helpers

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.CalendarContract
import java.util.TimeZone

private const val CALENDAR_DEFAULT_ID = 1L
private const val DEFAULT_PROCEDURE_LENGTH_MS = 3_600_000
private const val PROCEDURE_REMIND_MINUTES = 10
private const val MILLISECONDS_IN_MIN = 60_000

class CalendarHelper {

    fun addEvent(
        title: String,
        eventTimeMillis: Long,
        additionalRemindTimeMillis: Long,
        contentResolver: ContentResolver
    ) {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, eventTimeMillis)
            put(CalendarContract.Events.DTEND, eventTimeMillis + DEFAULT_PROCEDURE_LENGTH_MS)
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.CALENDAR_ID, CALENDAR_DEFAULT_ID)
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }
        val uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

        // get the event ID that is the last element in the Uri
        val eventID = uri?.lastPathSegment?.toLong()

        eventID?.let {
            val remindValues = ContentValues().apply {
                put(CalendarContract.Reminders.EVENT_ID, eventID)
                put(CalendarContract.Reminders.MINUTES, PROCEDURE_REMIND_MINUTES)
                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            }
            contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, remindValues)

            val additionalRemindMinutes = (eventTimeMillis - additionalRemindTimeMillis) / MILLISECONDS_IN_MIN
            val notificationRemindValues = ContentValues().apply {
                put(CalendarContract.Reminders.EVENT_ID, eventID)
                put(CalendarContract.Reminders.MINUTES, additionalRemindMinutes)
                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            }
            contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, notificationRemindValues)
        }
    }
}
