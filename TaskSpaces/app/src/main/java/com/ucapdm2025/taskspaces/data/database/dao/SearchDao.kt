package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity
import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import kotlinx.coroutines.flow.Flow

/**
 * SearchDao is an interface that defines the Data Access Object (DAO) for managing search operations in the database.
 */
@Dao
// TODO: Consider if it is necessary to add the user id for each search query to only
//show the results that belong to the user
interface SearchDao {
    @Query("SELECT * FROM workspace WHERE title LIKE '%' || :query || '%'")
    fun searchWorkspaces(query: String): Flow<List<WorkspaceEntity>>

    @Query("SELECT * FROM project WHERE title LIKE '%' || :query || '%'")
    fun searchProjects(query: String): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM task WHERE title LIKE '%' || :query || '%'")
    fun searchTasks(query: String): Flow<List<TaskEntity>>
}