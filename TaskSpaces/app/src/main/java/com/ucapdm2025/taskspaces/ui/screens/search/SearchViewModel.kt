package com.ucapdm2025.taskspaces.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.SearchModel
import com.ucapdm2025.taskspaces.data.repository.search.SearchRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing search functionality in the application.
 */
class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults: MutableStateFlow<SearchModel?> = MutableStateFlow(null)
    val searchResults = _searchResults.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            Log.d("SearchVewModel: search", _searchQuery.value)

            val response = searchRepository.search(query)

            if (response.isSuccess) {
                _searchResults.value = response.getOrNull()
                Log.d("SearchViewModel: search", "Search results: ${_searchResults.value}")
            } else {
                Log.e("SearchViewModel: search", "Error searching: ${response.exceptionOrNull()?.message}")
                _searchResults.value = null
            }
       }
    }

    fun setQuery(query: String) {
        _searchQuery.value = query
    }
}

/**
 * Factory for creating instances of SearchViewModel.
 *
 * @param searchRepository The repository used to perform search operations.
 */
class SearchViewModelFactory(
    private val searchRepository: SearchRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}