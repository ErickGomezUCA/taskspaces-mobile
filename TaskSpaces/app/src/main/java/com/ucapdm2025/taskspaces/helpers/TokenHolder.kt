package com.ucapdm2025.taskspaces.helpers

/**
 * TokenHolder is a singleton object that holds the authentication token.
 * It is used to provide the token for API requests.
 */
object TokenHolder {
    @Volatile
    var token: String = ""
}