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
import com.ucapdm2025.taskspaces.helpers.UiState


/**
 * BookmarkViewModel is a ViewModel that manages the state of bookmarked tasks.
 *
 * @param bookmarkRepository The repository used to manage bookmarks.
 */
class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {
    private val _bookmarkedTasksState = MutableStateFlow<UiState<List<TaskModel>>>(UiState.Loading)
    val bookmarkedTasksState: StateFlow<UiState<List<TaskModel>>> = _bookmarkedTasksState.asStateFlow()

    init {
        viewModelScope.launch {
            bookmarkRepository.getUserBookmarks().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _bookmarkedTasksState.value = UiState.Loading
                    }
                    is Resource.Success -> {
                        _bookmarkedTasksState.value = UiState.Success(resource.data)
                    }
                    is Resource.Error -> {
                        _bookmarkedTasksState.value = UiState.Error(resource.message)
                    }
                }
            }
        }
    }
}

/**
 * BookmarkViewModelFactory is a factory class for creating instances of BookmarkViewModel.
 *
 * @param bookmarkRepository The repository used to manage bookmarks.
 */
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