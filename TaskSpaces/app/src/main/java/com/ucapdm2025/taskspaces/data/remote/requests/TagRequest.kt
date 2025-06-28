package com.ucapdm2025.taskspaces.data.remote.requests

/**
 * Data class representing a request to create or update a tag.
 *
 * @property title The title of the tag.
 * @property color The color of the tag, represented as a string.
 */
data class TagRequest(
    val title: String,
    val color: String
)