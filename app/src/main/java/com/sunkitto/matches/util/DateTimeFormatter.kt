package com.sunkitto.matches.util

import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_TIME_PATTERN = "dd.MM.yyyy hh:mm"

fun Instant.formatDate(): String {
    val simpleDateFormat = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
    val date = Date(this.toEpochMilliseconds())
    return simpleDateFormat.format(date)
}
