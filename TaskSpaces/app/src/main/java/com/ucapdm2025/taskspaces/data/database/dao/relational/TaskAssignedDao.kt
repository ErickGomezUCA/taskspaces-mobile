package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskAssignedEntity

@Dao
interface TaskAssignedDao {
    @Query("SELECT * FROM user INNER JOIN task_assigned ON user.id = task_assigned.userId WHERE task_assigned.taskId = :taskId")
    fun getUsersByTaskId(taskId: Int): kotlinx.coroutines.flow.Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTaskAssigned(taskAssigned: TaskAssignedEntity)

    @Delete
    suspend fun deleteTaskAssigned(taskAssigned: TaskAssignedEntity)
}