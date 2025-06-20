package com.ucapdm2025.taskspaces.data.repository.search

import com.ucapdm2025.taskspaces.data.database.dao.SearchDao
import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.remote.services.SearchService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val searchDao: SearchDao,
    private val searchService: SearchService
): SearchRepository {
    override fun search(query: String): Flow<Resource<SearchModel>> = flow {
        emit(Resource.Loading)

        try {

        }
    }
}