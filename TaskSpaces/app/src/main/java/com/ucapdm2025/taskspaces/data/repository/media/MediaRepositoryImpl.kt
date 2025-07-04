package com.ucapdm2025.taskspaces.data.repository.media

import android.util.Log
import com.ucapdm2025.taskspaces.data.database.dao.MediaDao
import com.ucapdm2025.taskspaces.data.database.dao.relational.TaskMediaDao
import com.ucapdm2025.taskspaces.data.database.entities.MediaEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskMediaEntity
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.MediaModel
import com.ucapdm2025.taskspaces.data.remote.responses.ProjectResponse
import com.ucapdm2025.taskspaces.data.remote.responses.media.MediaResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.MediaService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.collections.forEach
import kotlin.collections.map

class MediaRepositoryImpl(
    private val mediaDao: MediaDao,
    private val taskMediaDao: TaskMediaDao,
    private val mediaService: MediaService
) : MediaRepository {
    override fun getMediaByTaskId(taskId: Int): Flow<Resource<List<MediaModel>>> =         flow {
//        emit(Resource.Loading)
//
//        try {
//            //            Fetch projects from remote
//            val remoteMedia: List<MediaResponse> =
//                mediaService.getM(workspaceId = workspaceId).content
//
//            //            Save remote projects to the database
//            if (remoteMedia.isNotEmpty()) {
//                remoteMedia.forEach {
//                    projectDao.createProject(it.toEntity())
//                }
//            }
//        } catch (e: Exception) {
//            Log.d(
//                "ProjectRepository: getProjectsByWorkspaceId",
//                "Error fetching projects: ${e.message}"
//            )
//        }
//
//        //        Use local projects
//        val localProjects =
//            projectDao.getProjectsByWorkspaceId(workspaceId = workspaceId).map { entities ->
//                val projects = entities.map { it.toDomain() }
//
//                if (projects.isEmpty()) {
//                    //                Logs an error if no projects are found for the user
//                    Resource.Error("No project found for workspace with ID: $workspaceId")
//                } else {
//                    //                Returns the projects as a success (to domain)
//                    Resource.Success(projects)
//                }
//            }.distinctUntilChanged()
//
//        emitAll(localProjects)
    }

    override suspend fun insertMediaToTask(
        taskId: Int,
        filename: String,
        type: String,
        url: String
    ): Result<MediaModel> {
        return try {
            // Create and insert MediaEntity
            val mediaEntity = MediaEntity(
                id = 0, // auto-generated
                filename = filename,
                type = type,
                url = url
            )
            mediaDao.insertMedia(mediaEntity)
            // Get the inserted media's id (assume url is unique for lookup)
            val insertedMedia = mediaDao.getAllMedia().map { list ->
                list.find { it.url == url }
            }.let { flow -> flow }
            // Insert into TaskMediaEntity
            // (This is a simplification; in practice, you may want to get the id in a better way)
            val mediaId = insertedMedia.firstOrNull()?.id ?: 0
            val taskMedia = TaskMediaEntity(taskId = taskId, mediaId = mediaId)
            taskMediaDao.insertTaskMedia(taskMedia)
            Result.success(mediaEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMedia(id: Int): Result<MediaModel> {
        return try {
            // Find the media entity
            val mediaEntity = mediaDao.getMediaById(id)
            val entity = mediaEntity.firstOrNull()
            if (entity != null) {
                mediaDao.deleteMedia(entity)
                Result.success(entity.toDomain())
            } else {
                Result.failure(Exception("Media not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

