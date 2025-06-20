package com.ucapdm2025.taskspaces.data.repository.search

import com.ucapdm2025.taskspaces.data.database.dao.SearchDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.remote.services.SearchService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val searchDao: SearchDao,
    private val searchService: SearchService
) : SearchRepository {
    override fun search(query: String): Flow<Resource<SearchModel>> = flow {
        emit(Resource.Loading)

//        Search locally only
//        TODO: Find a way to search to server when connected to the internet
//        and locally when offline
        var localWorkspaces: List<WorkspaceModel> = emptyList()
        var localProjects: List<ProjectModel> = emptyList()
        var localTasks: List<TaskModel> = emptyList()

        searchDao.searchWorkspaces(query).map { entities ->
            localWorkspaces = entities.map { it.toDomain() }
        }.distinctUntilChanged()

        searchDao.searchProjects(query).map { entities ->
            localProjects = entities.map { it.toDomain() }
        }.distinctUntilChanged()

        searchDao.searchTasks(query).map { entities ->
            localTasks = entities.map { it.toDomain() }
        }.distinctUntilChanged()

        val search: Flow<Resource<SearchModel>> = flowOf(
            Resource.Success(
                SearchModel(
                    workspaces = localWorkspaces,
                    projects = localProjects,
                    tasks = localTasks
                )
            )
        )

        emitAll(search)
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