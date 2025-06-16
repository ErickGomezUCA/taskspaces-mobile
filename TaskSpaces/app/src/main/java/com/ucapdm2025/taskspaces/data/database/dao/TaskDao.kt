package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * TaskDao is an interface that defines the Data Access Object (DAO) for managing tasks in the database.
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE projectId = :projectId")
    fun getTasksByProjectId(projectId: Int): Flow<List<TaskEntity>>
}