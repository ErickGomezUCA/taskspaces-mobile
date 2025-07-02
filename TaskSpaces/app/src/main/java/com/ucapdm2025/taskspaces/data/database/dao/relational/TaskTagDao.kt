package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.TagEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskTagEntity
import kotlinx.coroutines.flow.Flow

/**
 * TaskTagDao is an interface that defines the Data Access Object (DAO) for managing task tags in the database.
 */

@Dao
interface TaskTagDao {
    @Query("SELECT * FROM tag INNER JOIN task_tag ON tag.id = task_tag.tagId WHERE task_tag.taskId = :taskId")
    fun getTagsByTaskId(taskId: Int): Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTaskTag(taskTag: TaskTagEntity)

    @Update
    suspend fun updateTaskTag(taskTag: TaskTagEntity)

    @Delete
    suspend fun deleteTaskTag(taskTag: TaskTagEntity)
}