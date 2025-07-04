package com.ucapdm2025.taskspaces.utils

import androidx.compose.ui.graphics.Color

/**
 * Helper function to convert a #RRGGBBAA or #RRGGBB hex string to a Color instance.
 *
 * @return A Color instance corresponding to the hex string.
 */
fun String.toColorRRGGBBAA(): Color {
    val hex = this.removePrefix("#")
    val fullHex = when (hex.length) {
        8 -> hex
        6 -> hex + "FF" // Add full alpha if not present
        else -> throw IllegalArgumentException("Color string must be in format #RRGGBBAA or #RRGGBB")
    }
    val r = fullHex.substring(0, 2).toInt(16)
    val g = fullHex.substring(2, 4).toInt(16)
    val b = fullHex.substring(4, 6).toInt(16)
    val a = fullHex.substring(6, 8).toInt(16)
    return Color(r, g, b, a)
}