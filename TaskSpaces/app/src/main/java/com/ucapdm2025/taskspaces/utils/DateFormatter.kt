package com.ucapdm2025.taskspaces.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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