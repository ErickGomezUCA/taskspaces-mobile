package com.ucapdm2025.taskspaces.data.repository.tag

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.TagDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.TagRequest
import com.ucapdm2025.taskspaces.data.remote.responses.TagResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.TagService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import kotlin.collections.forEach

class TagRepositoryImpl(
    private val tagDao: TagDao,
    private val tagService: TagService
): TagRepository {
    override fun getTagsByProjectId(projectId: Int): Flow<Resource<List<TagModel>>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch tags from remote
            val remoteTags: List<TagResponse> =
                tagService.getTagsByProjectId(projectId = projectId).content

            //            Save remote tags to the database
            if (remoteTags.isNotEmpty()) {
                remoteTags.forEach {
                    tagDao.createTag(it.toEntity())
                }
            }
        } catch (e: Exception) {
            Log.d(
                "TagRepository: getTagsByProjectId",
                "Error fetching tags: ${e.message}"
            )
        }

        //        Use local tags
        val localTags =
            tagDao.getTagsByProjectId(projectId = projectId).map { entities ->
                val tags = entities.map { it.toDomain() }

                if (tags.isEmpty()) {
                    //                Logs an error if no tags are found for the user
                    Resource.Error("No tag found for project with ID: $projectId")
                } else {
                    //                Returns the tagas as a success (to domain)
                    Resource.Success(tags)
                }
            }.distinctUntilChanged()

        emitAll(localTags)
    }

    override fun getTagById(id: Int): Flow<Resource<TagModel?>> = flow{
        emit(Resource.Loading)

        try {
            //            Fetch project from remote
            val remoteTag: TagResponse? =
                tagService.getTagById(id = id).content

            //            Save remote project to the database
            if (remoteTag != null) {
                tagDao.createTag(remoteTag.toEntity())
            } else {
                Log.d("TagRepository", "No tag found with ID: $id")
            }
        } catch (e: Exception) {
            Log.d(
                "TagRepository: getTagById",
                "Error fetching tag: ${e.message}"
            )
        }

        //        Use local project
        val localTag =
            tagDao.getTagById(id = id).map { entity ->
                val tag = entity?.toDomain()

                if (tag == null) {
                    //                Logs an error if no tag are found for the user
                    Resource.Error("No tag found")
                } else {
                    //                Returns the tags as a success (to domain)
                    Resource.Success(tag)
                }
            }.distinctUntilChanged()

        emitAll(localTag)
    }

    override suspend fun createTag(title: String, color: String, projectId: Int): Result<TagModel> {
        val request = TagRequest(title, color)

        return try {
            val response =
                tagService.createTag(projectId = projectId, request = request)

            val createdTag: TagModel = response.content.toDomain()

//            Create retrieved tag from remote server into the local database
            tagDao.createTag(createdTag.toDatabase())

            Log.d(
                "TagRepository: createTag",
                "Tag created successfully: $createdTag"
            )

            Result.success(createdTag)
        } catch (e: HttpException) {
            Log.e("TagRepository", "Error creating tag: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TagRepository", "Network error creating tag: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TagRepository", "Unexpected error creating tag: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateTag(id: Int, title: String, color: String): Result<TagModel> {
        val request = TagRequest(title, color)

        return try {
            val response =
                tagService.updateTag(id = id, request = request)

            val updatedTag: TagModel = response.content.toDomain()

//            Update retrieved tag from remote server into the local database
            tagDao.updateTag(updatedTag.toDatabase())

            Log.d(
                "TagRepository: updateTag",
                "Tag updated successfully: $updatedTag"
            )

            Result.success(updatedTag)
        } catch (e: HttpException) {
            Log.e("TagRepository", "Error updating tag: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TagRepository", "Network error creating tag: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TagRepository", "Unexpected error creating tag: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteTag(id: Int): Result<TagModel> {
        return try {
            val response =
                tagService.deleteTag(id = id)

            val deletedTag: TagModel = response.content.toDomain()

//            Delete retrieved tag from remote server into the local database
            tagDao.deleteTag(deletedTag.toDatabase())

            Log.d(
                "TagRepository: deleteTag",
                "Tag deleted successfully: $deletedTag"
            )

            Result.success(deletedTag)
        } catch (e: HttpException) {
            Log.e("TagRepository", "Error deleting tag: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TagRepository", "Network error creating tag: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TagRepository", "Unexpected error creating tag: ${e.message}")
            Result.failure(e)
        }
    }
}

