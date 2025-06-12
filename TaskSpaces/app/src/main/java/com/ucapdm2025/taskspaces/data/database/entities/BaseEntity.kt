package com.ucapdm2025.taskspaces.data.database.entities

abstract class BaseEntity (
    open val id: Int,
    open val createdAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    open val updatedAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
)