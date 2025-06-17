package com.ucapdm2025.taskspaces.helpers

/**
 * Resource is a sealed class that represents the state of a resource.
 * It can be in one of three states: Loading, Success, or Error.
 *
 * @param T The type of data contained in the resource.
 */
sealed class Resource<out T> {
    object Loading: Resource<Nothing>()

    data class Success<out T>(val data: T): Resource<T>()

    data class Error(val message: String): Resource<Nothing>()
}