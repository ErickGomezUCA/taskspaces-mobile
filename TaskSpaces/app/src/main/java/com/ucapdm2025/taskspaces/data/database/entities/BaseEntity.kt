package com.ucapdm2025.taskspaces.data.database.entities

/**
 * BaseEntity is an abstract class that represents a base entity in the database.
 *
 * @property id The unique identifier for the entity.
 * @property createdAt The timestamp when the entity was created.
 * @property updatedAt The timestamp when the entity was last updated.
 */
abstract class BaseEntity (
    open val id: Int,
    open val createdAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    open val updatedAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
)