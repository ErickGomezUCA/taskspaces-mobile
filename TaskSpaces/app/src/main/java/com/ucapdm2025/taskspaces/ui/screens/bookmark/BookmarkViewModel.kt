package com.ucapdm2025.taskspaces.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {
    private val _bookmarkedTasks: MutableStateFlow<List<TaskModel>> = MutableStateFlow(emptyList())
    val bookmarkedTasks: StateFlow<List<TaskModel>> = _bookmarkedTasks.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.getUserBookmarks().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }
                    is Resource.Success -> {
                        val tasks = resource.data
                        _bookmarkedTasks.value = tasks
                    }
                    is Resource.Error -> {
                        // Handle error state if necessary
                    }
                }
            }
        }
    }
}

class BookmarkViewModelFactory(
    private val bookmarkRepository: BookmarkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(bookmarkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}