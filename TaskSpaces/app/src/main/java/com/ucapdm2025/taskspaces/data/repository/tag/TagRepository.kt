package com.ucapdm2025.taskspaces.data.repository.tag

import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * TagRepository is an interface that defines the methods for managing tags in the application.
 */
interface TagRepository {
    fun getTagsByProjectId(projectId: Int): Flow<Resource<List<TagModel>>>
    fun getTagsByTaskId(taskId: Int): Flow<Resource<List<TagModel>>>
    fun getTagById(id: Int): Flow<Resource<TagModel?>>
    suspend fun createTag(title: String, color: String, projectId: Int): Result<TagModel>
    suspend fun updateTag(id: Int, title: String, color: String): Result<TagModel>
    suspend fun deleteTag(id: Int): Result<TagModel>
    suspend fun assignTagToTask(tagId: Int, taskId: Int): Result<TagModel>
    suspend fun unassignTagFromTask(tagId: Int, taskId: Int): Result<TagModel>
}