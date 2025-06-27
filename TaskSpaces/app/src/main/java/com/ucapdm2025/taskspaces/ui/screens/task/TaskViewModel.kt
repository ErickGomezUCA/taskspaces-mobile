package com.ucapdm2025.taskspaces.ui.screens.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.lang.reflect.Member

/**
 * ViewModel for managing a single task's data, including its comments.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val commentRepository: CommentRepository = CommentRepositoryImpl()

    private val _currentTaskId: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val _task: MutableStateFlow<TaskModel?> = MutableStateFlow(null)
    val task: StateFlow<TaskModel?> = _task.asStateFlow()

    private val _isBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    private val _comments: MutableStateFlow<List<CommentModel>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<CommentModel>> = _comments.asStateFlow()


    init {
        // Use flatMapLatest to switch to the new task flow whenever _currentTaskId changes
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    taskRepository.getTaskById(taskId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val task = resource.data
                        _task.value = task
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }

                    null -> _task.value = null
                }
            }
        }

        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    commentRepository.getCommentsByTaskId(taskId)
                } else {
                    flowOf(emptyList()) // Emit empty list if no task ID is set
                }
            }.collect { comments ->
                _comments.value = comments
            }
        }

        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    bookmarkRepository.isBookmarked(taskId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val bookmark = resource.data
                        _isBookmarked.value = bookmark
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }

                    null -> _isBookmarked.value = false
                }
            }
        }
    }

    /**
     * Call this function from your Composable to load a new task.
     * This will trigger the flows in the init block to re-fetch data.
     */
    fun loadTask(taskId: Int) {
        if (_currentTaskId.value != taskId) { // Only update if the ID is different
            _currentTaskId.value = taskId
        }
    }

    // Function to clear the currently loaded task when the dialog is dismissed
    fun clearTask() {
        _currentTaskId.value = null
        _task.value = null // Explicitly clear the task model
        _comments.value = emptyList() // Explicitly clear comments
    }

    fun updateTask(
        id: Int,
        title: String,
        description: String? = null,
        deadline: String? = null,
        timer: Float? = null,
        status: StatusVariations = StatusVariations.PENDING,
    ) {
        viewModelScope.launch {
            val response = taskRepository.updateTask(
                id,
                title,
                description,
                deadline,
                timer,
                status,
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error updating task: ${exception.message}")
                }
            }
        }
    }

    fun createComment(content: String) {
        viewModelScope.launch {
            commentRepository.createComment(
                content = content,
                authorId = 1,
                taskId = _task.value?.id ?: 0
            )
        }
    }

    fun updateComment(id: Int, content: String) {
        viewModelScope.launch {
            commentRepository.updateComment(
                id = id,
                content = content,
                authorId = 1,
                taskId = _task.value?.id ?: 0
            )
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            commentRepository.deleteComment(id)
        }
    }

    //    TODO: Define how this function should work in the viewmodel
    fun bookmarkTask() {
        viewModelScope.launch {
            val response = bookmarkRepository.createBookmark(_currentTaskId.value ?: 0)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error bookmarking task: ${exception.message}")
                }
            }
        }
    }

    fun removeBookmarkTask() {
        viewModelScope.launch {
            val response = bookmarkRepository.deleteBookmark(_currentTaskId.value ?: 0)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error removing bookmark to task: ${exception.message}")
                }
            }
        }
    }

    suspend fun hasSufficientPermissions(
        minimumRole: MemberRoles
    ): Boolean {
        return memberRoleRepository.hasSufficientPermissions(
            taskId = taskId,
            minimumRole = minimumRole
        ).firstOrNull { it is Resource.Success || it is Resource.Error }?.let { resource ->
            when (resource) {
                is Resource.Success -> resource.data == true
                else -> false
            }
        } == true
    }

}

/**
 * Factory for creating instances of TaskViewModel.
 */
class TaskViewModelFactory(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskId, taskRepository, memberRoleRepository, bookmarkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}