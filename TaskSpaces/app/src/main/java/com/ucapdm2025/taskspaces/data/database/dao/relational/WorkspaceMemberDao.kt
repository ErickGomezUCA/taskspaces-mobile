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
    @Query("SELECT * FROM workspace_member WHERE workspaceId = :workspaceId")
    fun getMembersByWorkspaceId(workspaceId: Int): Flow<List<WorkspaceMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createMember(member: WorkspaceMemberEntity)

    @Update
    suspend fun updateMember(member: WorkspaceMemberEntity)

    @Delete
    suspend fun deleteMember(member: WorkspaceMemberEntity)
}