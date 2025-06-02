package com.ucapdm2025.taskspaces.ui.screens.test.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.Comment
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestTaskViewModel: ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl()
    private val commentRepository: CommentRepository = CommentRepositoryImpl()

    private val taskId = 1

    private val _task: MutableStateFlow<Task?> = MutableStateFlow(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).collect { task ->
                _task.value = task
            }
        }

        viewModelScope.launch {
            commentRepository.getCommentsByTaskId(taskId).collect { comments ->
                _comments.value = comments
            }
        }
    }

    fun updateTask(id: Int, title: String, description: String, status: String, projectId: Int) {
        viewModelScope.launch {
            taskRepository.updateTask(
                id = id,
                title = title,
                description = description,
                deadline = "",
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
}