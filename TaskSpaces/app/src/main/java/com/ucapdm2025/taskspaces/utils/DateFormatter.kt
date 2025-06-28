package com.ucapdm2025.taskspaces.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.ranges.contains

/**
 * Utility functions for converting between LocalDateTime and ISO 8601 formatted strings.
 */
fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

/**
 * Converts a LocalDateTime object to an ISO 8601 formatted string.
 */
fun LocalDateTime.toIsoString(): String =
    this.toInstant(ZoneOffset.UTC).toString()

/**
 * Formats a LocalDateTime object into a human-readable string.
 */
fun formatRelativeDate(date: LocalDateTime): String {
    val now = LocalDateTime.now()
    val days = ChronoUnit.DAYS.between(date.toLocalDate(), now.toLocalDate())
    return when {
        days == 0L -> "today"
        days == 1L -> "yesterday"
        days in 2..6 -> "$days days ago"
        days in 7..29 -> "${days / 7} week${if (days / 7 > 1) "s" else ""} ago"
        days >= 30 -> "${days / 30} month${if (days / 30 > 1) "s" else ""} ago"
        else -> ""
    }
}