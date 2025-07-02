package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity
import com.ucapdm2025.taskspaces.data.database.entities.TagEntity
import kotlinx.coroutines.flow.Flow

/**
 * TagDao is an interface that defines the Data Access Object (DAO) for managing tags in the database.
 */
@Dao
interface TagDao {
    @Query("SELECT * FROM tag WHERE projectId = :projectId")
    fun getTagsByProjectId(projectId: Int): Flow<List<TagEntity>>

//    Get tags by task id comes from the relational table: Task_Tag

    @Query("SELECT * FROM tag WHERE id = :id")
    fun getTagById(id: Int): Flow<TagEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTag(tag: TagEntity)

    @Update
    suspend fun updateTag(tag: TagEntity)

    @Delete
    suspend fun deleteTag(tag: TagEntity)
}