package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.relational.WorkspaceMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkspaceMemberDao {
    @Query("SELECT * FROM workspace_member WHERE workspaceId = :workspaceId AND userId != :requestUserId")
    fun getMembersByWorkspaceId(workspaceId: Int, requestUserId: Int): Flow<List<WorkspaceMemberEntity>>

    @Query("SELECT * FROM user INNER JOIN workspace_member ON user.id = workspace_member.userId WHERE workspace_member.workspaceId = :workspaceId AND user.id != :requestUserId")
    fun getUsersByWorkspaceId(workspaceId: Int, requestUserId: Int): Flow<List<WorkspaceMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createMember(member: WorkspaceMemberEntity)

    @Update
    suspend fun updateMember(member: WorkspaceMemberEntity)

    @Delete
    suspend fun deleteMember(member: WorkspaceMemberEntity)
}