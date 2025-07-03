package com.ucapdm2025.taskspaces.data.repository.search

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.SearchDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.services.SearchService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.io.IOException

class SearchRepositoryImpl(
    private val searchDao: SearchDao,
    private val searchService: SearchService
) : SearchRepository {
    override suspend fun search(query: String): Result<SearchModel> {
        return try {
            val response = searchService.search(query)

            Log.d("SearchRepository: search", "Search query: $query")
            Log.d("SearchRepository: search", "Search response: $response")

            val result = response.content.toDomain()

            Log.d("SearchRepository: search", "Search results: $result")

            Result.success(result)
        } catch (e: HttpException) {
            Log.e("SearchRepository: search", "Error searching: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("SearchRepository: search", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("SearchRepository: search", "Unexpected error: ${e.message}")
            Result.failure(e)
        }

    }

    override fun searchWorkspaces(query: String): Flow<Resource<List<WorkspaceModel>>> = flow {
        emit(Resource.Loading)

        val localWorkspaces = searchDao.searchWorkspaces(query).map { entities ->
            val results = entities.map { it.toDomain() }
            Resource.Success(results)
        }.distinctUntilChanged()

        emitAll(localWorkspaces)
    }

    override fun searchProjects(query: String): Flow<Resource<List<ProjectModel>>> = flow {
        emit(Resource.Loading)

        val localProjects = searchDao.searchProjects(query).map { entities ->
            val results = entities.map { it.toDomain() }
            Resource.Success(results)
        }.distinctUntilChanged()

        emitAll(localProjects)
    }

    override fun searchTasks(query: String): Flow<Resource<List<TaskModel>>> = flow {
        emit(Resource.Loading)

        val localTasks = searchDao.searchTasks(query).map { entities ->
            val results = entities.map { it.toDomain() }
            Resource.Success(results)
        }.distinctUntilChanged()

        emitAll(localTasks)
    }
}