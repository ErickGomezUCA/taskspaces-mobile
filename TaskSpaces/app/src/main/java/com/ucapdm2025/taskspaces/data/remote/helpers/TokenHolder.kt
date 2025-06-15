package com.ucapdm2025.taskspaces.data.remote.helpers

object TokenHolder {
    @Volatile
    var token: String = ""
}