package com.tangpj.github.utils

import android.content.Context
import com.tangpj.github.R
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


fun LocalDateTime.getTimeline(
        context: Context) : String{
    val now = LocalDateTime.now()
    val duration = Duration.between(this, now)
    return when{
        duration.seconds  < 60 -> "${duration.seconds} ${context.getString(R.string.seconds_ago)}"
        duration.toMinutes() < 60 -> "${duration.toMinutes()} ${context.getString(R.string.minutes_ago)}"
        duration.toHours() < 60 -> "${duration.toHours()} ${context.getString(R.string.hours_ago)}"
        duration.toDays() < 7 -> "${duration.toDays()} ${context.getString(R.string.days_ago)}"
        else -> DateTimeFormatter.BASIC_ISO_DATE.format(this)
    }
}