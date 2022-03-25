package com.isop.podcastapp.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
//2022-03-24T17:55:20Z
fun Long.formatMillisecondsAsDate(
    pattern: String = "yyyy-MM-dd HH:mm:ss"
): String {
    val dateFormatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    val instant = Instant.ofEpochMilli(this)
    return dateFormatter.format(instant)
}