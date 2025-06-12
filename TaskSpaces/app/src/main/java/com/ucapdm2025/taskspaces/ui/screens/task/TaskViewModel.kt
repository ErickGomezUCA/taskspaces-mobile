package com.ucapdm2025.taskspaces.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(taskId: Int) : ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl()
    private val commentRepository: CommentRepository = CommentRepositoryImpl()

    private val _task: MutableStateFlow<TaskModel?> = MutableStateFlow(null)
    val task: StateFlow<TaskModel?> = _task.asStateFlow()

    private val _comments: MutableStateFlow<List<CommentModel>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<CommentModel>> = _comments.asStateFlow()

    private val _currentTaskId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val currentTaskId: StateFlow<Int?> = _currentTaskId.asStateFlow()

    init {
        // Use flatMapLatest to switch to the new task flow whenever _currentTaskId changes
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    taskRepository.getTaskById(taskId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { task ->
                _task.value = task
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
        description: String,
        status: StatusVariations,
        projectId: Int
    ) {
        viewModelScope.launch {
            taskRepository.updateTask(
                id = id,
                title = title,
                description = description,
                deadline = LocalDateTime.now(),
                status = status,
                breadcrumb = "Workspace 1 / Project 1",
                projectId = projectId
            )
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
            task.value?.let { task ->
                taskRepository.bookmarkTask(task.id)
            }
        }
    }
}

class TaskViewModelFactory(private val taskId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(taskId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}