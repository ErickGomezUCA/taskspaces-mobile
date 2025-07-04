package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.MediaEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskMediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskMediaDao {
    @Query("SELECT * FROM media INNER JOIN task_media ON media.id = task_media.mediaId WHERE taskId = :taskId")
    fun getMediaByTaskId(taskId: Int): Flow<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskMedia(taskMedia: TaskMediaEntity)

    @Update
    suspend fun updateTaskMedia(taskMedia: TaskMediaEntity)

    @Delete
    suspend fun deleteTaskMedia(taskMedia: TaskMediaEntity)
}