package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkspaceDao {
    @Query("SELECT * FROM workspace WHERE ownerId = :ownerId")
    fun getWorkspacesByUserId(ownerId: Int): Flow<List<WorkspaceEntity>>

//    TODO: Get workspaces shared with the user

    @Query("SELECT * FROM workspace WHERE id = :id")
    fun getWorkspaceById(id: Int): Flow<WorkspaceEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createWorkspace(workspace: WorkspaceEntity)

    @Update
    suspend fun updateWorkspace(workspace: WorkspaceEntity)

    @Delete
    suspend fun deleteWorkspace(workspace: WorkspaceEntity)

//    TODO: Set members queries
}