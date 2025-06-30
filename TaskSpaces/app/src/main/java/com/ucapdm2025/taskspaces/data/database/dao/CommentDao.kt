package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.CommentEntity
import kotlinx.coroutines.flow.Flow

/**
 * CommentDao is an interface that defines the Data Access Object (DAO) for managing comments in the database.
 */
@Dao
interface CommentDao {
    @Query("SELECT * FROM comment WHERE taskId = :taskId")
    fun getCommentsByTaskId(taskId: Int): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createComment(comment: CommentEntity)

    @Update
    suspend fun updateComment(comment: CommentEntity)

    @Delete
    suspend fun deleteComment(comment: CommentEntity)
}