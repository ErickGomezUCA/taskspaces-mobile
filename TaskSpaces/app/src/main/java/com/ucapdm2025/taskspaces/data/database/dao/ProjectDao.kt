package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

/**
 * ProjectDao is an interface that defines the Data Access Object (DAO) for managing projects in the database.
 * It provides methods to retrieve, create, update, and delete projects.
 */
@Dao
interface ProjectDao {
   @Query("SELECT * FROM project WHERE workspaceId = :workspaceId")
   fun getProjectsByWorkspaceId(workspaceId: Int): Flow<List<ProjectEntity>>

   @Query("SELECT * FROM project WHERE id = :id")
    fun getProjectById(id: Int): Flow<ProjectEntity?>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun createProject(project: ProjectEntity)

   @Update
   suspend fun updateProject(project: ProjectEntity)

   @Delete
    suspend fun deleteProject(project: ProjectEntity)
}