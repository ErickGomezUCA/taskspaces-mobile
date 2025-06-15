package com.ucapdm2025.taskspaces.data.repository.project

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.ProjectDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.ProjectRequest
import com.ucapdm2025.taskspaces.data.remote.responses.ProjectResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.ProjectService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okio.IOException

/**
 * ProjectRepositoryImpl is an implementation of the ProjectRepository interface.
 * It provides methods to manage projects in the application, including retrieving,
 * creating, updating, and deleting projects.
 */
class ProjectRepositoryImpl(
    private val projectDao: ProjectDao,
    private val projectService: ProjectService
) : ProjectRepository {
    override fun getProjectsByWorkspaceId(workspaceId: Int): Flow<Resource<List<ProjectModel>>> =
        flow {
            emit(Resource.Loading)

            try {
                //            Fetch projects from remote
                val remoteProjects: List<ProjectResponse> =
                    projectService.getProjectsByWorkspaceId(workspaceId = workspaceId).content

                //            Save remote projects to the database
                if (remoteProjects.isNotEmpty()) {
                    remoteProjects.forEach {
                        projectDao.createProject(it.toEntity())
                    }
                }
            } catch (e: Exception) {
                Log.d(
                    "ProjectRepository: getProjectsByWorkspaceId",
                    "Error fetching projects: ${e.message}"
                )
            }

            //        Use local projects
            val localProjects =
                projectDao.getProjectsByWorkspaceId(workspaceId = workspaceId).map { entities ->
                    val projects = entities.map { it.toDomain() }

                    if (projects.isEmpty()) {
                        //                Logs an error if no projects are found for the user
                        Resource.Error("No project found for workspace with ID: $workspaceId")
                    } else {
                        //                Returns the projects as a success (to domain)
                        Resource.Success(projects)
                    }
                }.distinctUntilChanged()

            emitAll(localProjects)
        }.flowOn(Dispatchers.IO)

    override suspend fun getProjectById(id: Int): Flow<Resource<ProjectModel?>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch project from remote
            val remoteProject: ProjectResponse? =
                projectService.getProjectById(id = id).content

            //            Save remote project to the database
            if (remoteProject != null) {
                projectDao.createProject(remoteProject.toEntity())
            } else {
                Log.d("ProjectRepository", "No project found with ID: $id")
            }
        } catch (e: Exception) {
            Log.d(
                "ProjectRepository: getProjectById",
                "Error fetching project: ${e.message}"
            )
        }

        //        Use local project
        val localProject =
            projectDao.getProjectById(id = id).map { entity ->
                val project = entity?.toDomain()

                if (project == null) {
                    //                Logs an error if no projects are found for the user
                    Resource.Error("No project found")
                } else {
                    //                Returns the projects as a success (to domain)
                    Resource.Success(project)
                }
            }.distinctUntilChanged()

        emitAll(localProject)
    }

    override suspend fun createProject(
        title: String,
        icon: String,
        workspaceId: Int
    ): Result<ProjectModel> {
        val request = ProjectRequest(title, icon)

        return try {
            val response =
                projectService.createProject(workspaceId = workspaceId, request = request)

            val createdProject: ProjectModel = response.content.toDomain()

//            Create retrieved project from remote server into the local database
            projectDao.createProject(createdProject.toDatabase())

            Log.d(
                "ProjectRepository: createProject",
                "Project created successfully: $createdProject"
            )

            Result.success(createdProject)
        } catch (e: HttpException) {
            Log.e("ProjectRepository", "Error creating project: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("ProjectRepository", "Network error creating project: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("ProjectRepository", "Unexpected error creating project: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateProject(
        id: Int,
        title: String,
        icon: String,
        workspaceId: Int
    ): Result<ProjectModel> {
        val request = ProjectRequest(title, icon)

        return try {
            val response = projectService.updateProject(id = id, request = request)

            val updatedProject: ProjectModel = response.content.toDomain()

//            Update retrieved project from remote server into the local database
            projectDao.updateProject(updatedProject.toDatabase())

            Log.d(
                "ProjectRepository: updateProject",
                "Project updated successfully: $updatedProject"
            )

            Result.success(updatedProject)
        } catch (e: HttpException) {
            Log.e("ProjectRepository", "Error updating project: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("ProjectRepository", "Network error creating project: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("ProjectRepository", "Unexpected error creating project: ${e.message}")
            Result.failure(e)
        }
    }


    override suspend fun deleteProject(id: Int): Result<ProjectModel> {
        return try {
            val response = projectService.deleteProject(id = id)

            val deletedProject: ProjectModel = response.content.toDomain()

//            Update retrieved project from remote server into the local database
            projectDao.deleteProject(deletedProject.toDatabase())

            Log.d(
                "ProjectRepository: deleteProject",
                "Project deleted successfully: $deletedProject"
            )

            Result.success(deletedProject)
        } catch (e: HttpException) {
            Log.e("ProjectRepository", "Error deleting project: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("ProjectRepository", "Network error creating project: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("ProjectRepository", "Unexpected error creating project: ${e.message}")
            Result.failure(e)
        }
    }
}