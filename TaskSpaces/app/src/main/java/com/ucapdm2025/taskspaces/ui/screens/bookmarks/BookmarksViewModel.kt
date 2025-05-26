package com.ucapdm2025.taskspaces.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookmarksViewModel: ViewModel() {
    private val tasksRepository: TaskRepository = TaskRepositoryImpl()

    private val _bookmarkedTasks = MutableStateFlow<List<Task>>(emptyList())
    val bookmarkedTasks = _bookmarkedTasks

    init {
        viewModelScope.launch {
            tasksRepository.getBookmarkedTasks().collect { list ->
                _bookmarkedTasks.value = list
            }
        }
    }
}