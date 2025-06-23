package com.ucapdm2025.taskspaces.data.database.entities.catalog

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.database.entities.BaseEntity

/**
 * MemberRoleEntity is a data class that represents a member role in the database.
 * It extends BaseEntity to include common fields such as id, createdAt, and updatedAt.
 *
 * @property id The unique identifier for the member role.
 * @property value The value of the member role (e.g., "admin", "member").
 * @property createdAt The timestamp when the member role was created.
 * @property updatedAt The timestamp when the member role was last updated.
 */
@Entity(tableName = "member_role")
data class MemberRoleEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val value: String,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
) : BaseEntity(id, createdAt, updatedAt)