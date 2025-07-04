package com.ucapdm2025.taskspaces.data.database.dao.catalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucapdm2025.taskspaces.data.database.entities.catalog.MemberRoleEntity

/**
 * MemberRoleDao is an interface that defines the Data Access Object (DAO) for managing member roles in the database.
 * It provides methods to retrieve and insert member roles.
 */
@Dao
interface MemberRoleDao {
    @Query("SELECT * FROM member_role WHERE id = :id")
    suspend fun getMemberRoleById(id: Int): MemberRoleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemberRole(memberRole: MemberRoleEntity)
}