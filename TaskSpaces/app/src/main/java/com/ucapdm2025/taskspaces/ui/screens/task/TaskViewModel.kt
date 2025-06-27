package com.ucapdm2025.taskspaces.ui.screens.task

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.tag.TagRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * ViewModel for managing a single task's data, including its comments.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository,
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

    private val _showTagsDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTagsDialog: StateFlow<Boolean> = _showTagsDialog.asStateFlow()

    private val _tags: MutableStateFlow<List<TagModel>> = MutableStateFlow(emptyList())
    val tags: StateFlow<List<TagModel>> = _tags.asStateFlow()

    private val _projectTags: MutableStateFlow<List<TagModel>> = MutableStateFlow(emptyList())
    val projectTags: StateFlow<List<TagModel>> = _projectTags.asStateFlow()

    private val _showTaskMembersDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTaskMembersDialog: StateFlow<Boolean> = _showTaskMembersDialog.asStateFlow()

    private val _members: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val members: StateFlow<List<UserModel>> = _members.asStateFlow()

    init {
        // Use flatMapLatest to switch to the new task flow whenever _currentTaskId changes
//        Load _task
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

//        Load _comments
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

//        Load _isBookmarked
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

        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    tagRepository.getTagsByTaskId(taskId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val tags = resource.data
                        _tags.value = tags
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }

                    null -> _isBookmarked.value = false
                }
            }
        }

//        Load _projectTags
        viewModelScope.launch {
            _task.flatMapLatest { task ->
                if (task != null) {
                    tagRepository.getTagsByProjectId(task.projectId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val tags = resource.data
                        _projectTags.value = tags
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }

                    null -> _projectTags.value = emptyList()
                }
            }
        }


//        Load _members
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { taskId ->
                if (taskId != null) {
                    taskRepository.getAssignedUsersByTaskId(taskId)
                } else {
                    flowOf(null) // Emit null if no task ID is set
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val members = resource.data
                        _members.value = members
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }

                    null -> _members.value = emptyList()
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

    fun setTaskData(
        title: String? = null,
        status: StatusVariations? = null,
        description: String? = null,
        tags: List<String>? = null, // TODO: Change to tag model
        media: List<String>? = null, // TODO: Change to media model
        deadline: String? = null,
        timer: Float? = null,
        members: List<Int>? = null, // TODO: Change to member model
    ) {
        val previousTask = _task.value

        _task.value = previousTask?.copy(
            title = title ?: previousTask.title,
            status = status ?: previousTask.status,
            description = description ?: previousTask.description,
            deadline = deadline ?: previousTask.deadline,
            timer = timer ?: previousTask.timer,
        )
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

//    Tags
    fun showTagsDialog() {
        _showTagsDialog.value = true
    }

    fun hideTagsDialog() {
        _showTagsDialog.value = false
    }

    fun addTag(title: String, color: Color) {
        var createdTag: Int = 0

        viewModelScope.launch {
            val response = tagRepository.createTag(
                title = title,
                color = color.toArgb().toString(), // Convert Color to Int
                projectId = _task.value?.projectId ?: 0
            )

            if (response.isSuccess) {
                createdTag = response.getOrNull()?.id ?: 0
            } else {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error creating tag: ${exception.message}")
                }
            }
        }

//        After creating the tag, assign it to the task
        viewModelScope.launch {
            if (createdTag != 0 && _task.value != null) {
                val response = tagRepository.assignTagToTask(
                    tagId = createdTag,
                    taskId = _task.value!!.id
                )

                if (!response.isSuccess) {
                    // Handle error, e.g., show a message to the user
                    val exception = response.exceptionOrNull()
                    if (exception != null) {
                        // Log or handle the exception as needed
                        Log.e("TaskViewModel", "Error adding tag to task: ${exception.message}")
                    }
                }
            }
        }
    }

    fun updateTag(
        id: Int,
        title: String,
        color: Color
    ) {
        viewModelScope.launch {
            val response = tagRepository.updateTag(
                id = id,
                title = title,
                color = color.toArgb().toString() // Convert Color to Int
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error updating tag: ${exception.message}")
                }
            }
        }
    }

    fun deleteTag(
        id: Int
    ) {
        viewModelScope.launch {
//            Unassign the tag from the task first
            val responseTaskTag = tagRepository.unassignTagFromTask(
                tagId = id,
                taskId = _task.value?.id ?: 0
            )

//            Then delete the tag
            if (responseTaskTag.isSuccess) {
                val response = tagRepository.deleteTag(id)

                if (!response.isSuccess) {
                    // Handle error, e.g., show a message to the user
                    val exception = response.exceptionOrNull()
                    if (exception != null) {
                        // Log or handle the exception as needed
                        Log.e("TaskViewModel", "Error deleting tag: ${exception.message}")
                    }
                }
            } else {
                // Handle error, e.g., show a message to the user
                val exception = responseTaskTag.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error unassigning tag: ${exception.message}")
                }
            }
        }
    }

    fun assignTagToTask(
        tagId: Int
    ) {
        viewModelScope.launch {
            val response = tagRepository.assignTagToTask(
                tagId = tagId,
                taskId = _task.value?.id ?: 0
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error assigning tag to task: ${exception.message}")
                }
            }
        }
    }

    fun unassignTagFromTask(
        tagId: Int
    ) {
        viewModelScope.launch {
            val response = tagRepository.unassignTagFromTask(
                tagId = tagId,
                taskId = _task.value?.id ?: 0
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error assigning tag to task: ${exception.message}")
                }
            }
        }
    }

    //    Task members
    fun showTaskMembersDialog() {
        _showTaskMembersDialog.value = true
    }

    fun hideTaskMembersDialog() {
        _showTaskMembersDialog.value = false
    }

    fun assignMemberToTask(
        username: String
    ) {
        viewModelScope.launch {
            val response = taskRepository.assignMemberToTask(
                username = username,
                taskId = _task.value?.id ?: 0
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error assigning member to task: ${exception.message}")
                }
            }
        }
    }

    fun unassignMemberFromTask(
        userId: Int
    ) {
        viewModelScope.launch {
            val response = taskRepository.unassignMemberFromTask(
                userId = userId,
                taskId = _task.value?.id ?: 0
            )

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error unassigning member from task: ${exception.message}")
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
}

/**
 * Factory for creating instances of TaskViewModel.
 */
class TaskViewModelFactory(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskId, taskRepository, tagRepository, bookmarkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}