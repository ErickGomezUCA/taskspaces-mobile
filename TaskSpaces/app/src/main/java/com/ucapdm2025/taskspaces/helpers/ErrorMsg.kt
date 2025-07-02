package com.ucapdm2025.taskspaces.helpers

/**
 * Converts technical or HTTP messages into user-friendly text.
 */
fun friendlyMessage(raw: String, fallback: String): String = when {
    "404" in raw -> "Resource not found"
    "403" in raw -> "Permission denied"
    "409" in raw -> "User already belongs to the workspace"
    "500" in raw -> "Server error, please try again later"
    raw.contains("timeout", true) -> "The operation took too long"
    raw.contains("unable", true) -> fallback
    raw.isBlank() -> fallback
    else -> raw
}