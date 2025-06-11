package com.ucapdm2025.taskspaces.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TaskViewModel: ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl()
    private val commentRepository: CommentRepository = CommentRepositoryImpl()

    private val taskId = 1

    private val _taskModel: MutableStateFlow<TaskModel?> = MutableStateFlow(null)
    val taskModel: StateFlow<TaskModel?> = _taskModel.asStateFlow()

    private val _comments: MutableStateFlow<List<CommentModel>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<CommentModel>> = _comments.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).collect { task ->
                _taskModel.value = task
            }
        }

        viewModelScope.launch {
            commentRepository.getCommentsByTaskId(taskId).collect { comments ->
                _comments.value = comments
            }
        }
    }

    fun updateTask(id: Int, title: String, description: String, status: StatusVariations, projectId: Int) {
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
            commentRepository.createComment(content = content, authorId = 1, taskId = taskId)
        }
    }

    fun updateComment(id: Int, content: String) {
        viewModelScope.launch {
            commentRepository.updateComment(id = id, content = content, authorId = 1, taskId = taskId)
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
            taskModel.value?.let { task ->
                taskRepository.bookmarkTask(task.id)
            }
        }
    }
}