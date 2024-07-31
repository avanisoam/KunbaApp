package com.example.kunbaapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.intl.Locale
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDate(millis: Long) : LocalDate {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

   // @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDateWithFormatter(date: LocalDate, dateTimeFormatter: DateTimeFormatter) : LocalDate {
        //Convert the date to a long in millis using a dateformmater
        val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        //Convert the millis to a localDate object
        return Instant
            .ofEpochMilli(dateInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }


   // @RequiresApi(Build.VERSION_CODES.O)
    fun dateToString(date: LocalDate): String {
       //val text = LocalDateTime.parse(date.toString())
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy")
        val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
        return dateFormatter.format(dateInMillis)
    }
}