package com.ucapdm2025.taskspaces.data.remote.requests

/**
 * CommentRequest is a data class that represents a request to create or update a comment.
 * It currently does not contain any properties, but can be extended in the future
 * to include fields such as content, authorId, and taskId.
 *
 * @property content The content of the comment.
 */
data class CommentRequest (
    val content: String,
)