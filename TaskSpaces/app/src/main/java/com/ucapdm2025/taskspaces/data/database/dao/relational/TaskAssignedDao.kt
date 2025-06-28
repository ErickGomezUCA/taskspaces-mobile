package com.ucapdm2025.taskspaces.data.database.dao.relational

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskAssignedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskAssignedDao {
    @Query("SELECT * FROM user INNER JOIN task_assigned ON user.id = task_assigned.userId WHERE task_assigned.taskId = :taskId")
    fun getUsersByTaskId(taskId: Int): Flow<List<UserEntity>>

    @Query("SELECT * FROM task INNER JOIN task_assigned ON task.id = task_assigned.taskId WHERE task_assigned.userId = :userId")
    fun getTasksByUserId(userId: Int): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTaskAssigned(taskAssigned: TaskAssignedEntity)

    @Delete
    suspend fun deleteTaskAssigned(taskAssigned: TaskAssignedEntity)
}