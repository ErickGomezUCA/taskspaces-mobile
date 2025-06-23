package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.relational.BookmarkEntity
import kotlinx.coroutines.flow.Flow

/**
 * BookmarkDao is an interface that defines the Data Access Object (DAO) for managing bookmarks in the database.
 */
@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark WHERE userId = :userId")
    fun getBookmarksByUserId(userId: Int): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmark WHERE userId = :userId AND taskId = :taskId")
    fun getBookmarkByUserIdAndTaskId(userId: Int, taskId: Int): Flow<BookmarkEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun createBookmark(bookmark: BookmarkEntity)

    @Update
    suspend fun updateBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
}