package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * TaskDao is an interface that defines the Data Access Object (DAO) for managing tasks in the database.
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE projectId = :projectId")
    fun getTasksByProjectId(projectId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}