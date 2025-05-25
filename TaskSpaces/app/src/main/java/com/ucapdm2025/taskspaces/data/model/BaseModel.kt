package com.ucapdm2025.taskspaces.data.model

/**
 * BaseModel is an abstract class that represents a base model with common properties
 * for every defined model.
 *
 * @property id The unique identifier for the model.
 * @property createdAt The timestamp when the model was created.
 * @property updatedAt The timestamp when the model was last updated.
 */
abstract class BaseModel (
    open val id: Int,
    open val createdAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
    open val updatedAt: String, // TODO: See if it is needed to parse into Date object or DateTime instead of String
)