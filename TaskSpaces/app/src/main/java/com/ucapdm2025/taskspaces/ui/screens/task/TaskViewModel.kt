package com.ucapdm2025.taskspaces.ui.screens.task

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepository
import com.ucapdm2025.taskspaces.data.repository.tag.TagRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.helpers.UiState
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
import java.time.LocalDateTime

/**
 * ViewModel for managing a single task's data, including its comments.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val taskId: Int,
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _currentTaskId: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val _task: MutableStateFlow<TaskModel?> = MutableStateFlow(null)
    val task: StateFlow<TaskModel?> = _task.asStateFlow()

    private val _isBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

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

    private val _workspaceMembers: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val workspaceMembers: StateFlow<List<UserModel>> = _workspaceMembers.asStateFlow()

    private val _comments: MutableStateFlow<List<CommentModel>> = MutableStateFlow(emptyList())
    val comments: StateFlow<List<CommentModel>> = _comments.asStateFlow()

    private val _showUpdateCommentDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showUpdateCommentDialog: StateFlow<Boolean> = _showUpdateCommentDialog.asStateFlow()

    private val _selectedCommentToUpdate: MutableStateFlow<CommentModel?> = MutableStateFlow(null)
    val selectedCommentToUpdate: StateFlow<CommentModel?> = _selectedCommentToUpdate.asStateFlow()

    private val _newComment: MutableStateFlow<String> = MutableStateFlow("")
    val newComment: StateFlow<String> = _newComment.asStateFlow()

    private val _taskState = MutableStateFlow<UiState<TaskModel>>(UiState.Loading)
    val taskState: StateFlow<UiState<TaskModel>> = _taskState.asStateFlow()


    init {
        // Use flatMapLatest to switch to the new task flow whenever _currentTaskId changes
//        Load _task
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let { taskRepository.getTaskById(it) }
                    ?: flowOf(null) // Emit null if no task ID is set

            }.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                        _taskState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        val taskData = resource.data
                        _task.value = taskData
                        _taskState.value = UiState.Success(taskData!!)

                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                        _taskState.value = UiState.Error(
                            resource.message ?: "Unknown error loading task"
                        )
                    }

                    null -> {
                        _task.value = null
                        _taskState.value = UiState.Error("Task not found")
                    }
                }
            }
        }

//        Load _isBookmarked
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let {
                    bookmarkRepository.isBookmarked(it)
                } ?: flowOf(null) // Emit null if no task ID is set
            }.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val bookmark = resource.data
                        _isBookmarked.value = bookmark
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                        _isBookmarked.value = false
                    }

                    null, is Resource.Loading -> {
                        // Handle loading state if necessary
                    }
                }
            }
        }

//        Load _tags
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let {
                    tagRepository.getTagsByTaskId(it)
                } ?: flowOf(null) // Emit null if no task ID is set
            }.collect { resource ->
                when (resource) {

                    is Resource.Success ->
                        _tags.value = resource.data


                    is Resource.Error,
                    null -> _tags.value = emptyList()// Handle error state if necessary
                    else -> {}
                }
            }
        }

//        Load _projectTags
        viewModelScope.launch {
            _task.flatMapLatest { task ->
                task?.let {
                    tagRepository.getTagsByProjectId(it.projectId)
                } ?: flowOf(null) // Emit null if no task ID is set

            }.collect { resource ->
                when (resource) {

                    is Resource.Success ->
                        _projectTags.value = resource.data


                    is Resource.Error, null -> _projectTags.value = emptyList()
                    else -> {}
                }
            }
        }


//        Load _members
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let {
                    taskRepository.getAssignedMembersByTaskId(taskId)
                } ?: flowOf(null) // Emit null if no task ID is set
            }.collect { resource ->
                when (resource) {

                    is Resource.Success ->
                        _members.value = resource.data


                    is Resource.Error, null -> _members.value = emptyList()
                    else -> {}
                }
            }
        }

//        Load _workspaceMembers
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let {
                    taskRepository.getWorkspaceMembersByTaskId(taskId)
                } ?: flowOf(null) // Emit null if no task ID is set

            }.collect { resource ->
                when (resource) {

                    is Resource.Success ->
                        _workspaceMembers.value = resource.data


                    is Resource.Error, null -> _workspaceMembers.value = emptyList()
                    else -> {}
                }
            }
        }

//        Load _comments
        viewModelScope.launch {
            _currentTaskId.flatMapLatest { id ->
                id?.let {
                    commentRepository.getCommentsByTaskId(taskId)
                } ?: flowOf(null) // Emit null if no task ID is set

            }.collect { resource ->
                when (resource) {
                    is Resource.Success ->
                        _comments.value = resource.data

                    is Resource.Error, null -> _comments.value = emptyList()
                    else -> {}
                }
            }
        }
    }

    /**
     * Call this function from your Composable to load a new task.
     * This will trigger the flows in the init block to re-fetch data.
     */
    fun loadTask(taskId: Int) {
        if (_currentTaskId.value != taskId) {// Only update if the ID is different
            _taskState.value = UiState.Loading
            _currentTaskId.value = taskId
        }
    }

    // Function to clear the currently loaded task when the dialog is dismissed
    fun clearTask() {
        _currentTaskId.value = null
        _task.value = null // Explicitly clear the task model
        _taskState.value = UiState.Loading
        _comments.value = emptyList() // Explicitly clear comments
    }

    fun setTaskData(
        title: String? = null,
        status: StatusVariations? = null,
        description: String? = null,
        media: List<String>? = null, // TODO: Change to media model
        deadline: LocalDateTime? = null,
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

    fun clearDeadline() {
        _task.value = _task.value?.copy(deadline = null)
    }

    fun updateTask(
        id: Int,
        title: String,
        description: String? = null,
        deadline: LocalDateTime? = null,
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
            val r = (color.red * 255).toInt()
            val g = (color.green * 255).toInt()
            val b = (color.blue * 255).toInt()
            val a = (color.alpha * 255).toInt()
            val hexColor = String.format("#%02X%02X%02X%02X", r, g, b, a)

            Log.d("TaskViewModel", "Adding tag with title: $title and color: ${hexColor}")

            val response = tagRepository.createTag(
                title = title,
                color = hexColor, // Convert Color to #RRGGBBAA
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
            val r = (color.red * 255).toInt()
            val g = (color.green * 255).toInt()
            val b = (color.blue * 255).toInt()
            val a = (color.alpha * 255).toInt()
            val hexColor = String.format("#%02X%02X%02X%02X", r, g, b, a)

            Log.d(
                "TaskViewModel",
                "Updating tag with id: $id, title: $title and color: ${hexColor}"
            )

            val response = tagRepository.updateTag(
                id = id,
                title = title,
                color = hexColor
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
//            Then delete the tag
            val response = tagRepository.deleteTag(id)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error deleting tag: ${exception.message}")
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
            } else {
                // Force reload tags after unassign
                _currentTaskId.value?.let { reloadTags(it) }
            }
        }
    }

    fun reloadTags(taskId: Int = _currentTaskId.value ?: 0) {
        viewModelScope.launch {
            tagRepository.getTagsByTaskId(taskId).collect { resource ->
                when (resource) {
                    is Resource.Success -> _tags.value = resource.data
                    is Resource.Error, null -> _tags.value = emptyList()
                    else -> {}
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
        userId: Int
    ) {
        viewModelScope.launch {
            val response = taskRepository.assignMemberToTask(
                userId = userId,
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
                    Log.e(
                        "TaskViewModel",
                        "Error unassigning member from task: ${exception.message}"
                    )
                }
            } else {
                _currentTaskId.value?.let { reloadMembers(it) }
            }
        }
    }

    fun reloadMembers(taskId: Int = _currentTaskId.value ?: 0) {
        viewModelScope.launch {
            taskRepository.getAssignedMembersByTaskId(taskId).collect { resource ->
                when (resource) {
                    is Resource.Success -> _members.value = resource.data
                    is Resource.Error, null -> _members.value = emptyList()
                    else -> {}
                }
            }
        }
    }

    //  Comments
    fun showUpdateCommentDialog() {
        _showUpdateCommentDialog.value = true
    }

    fun hideUpdateCommentDialog() {
        _showUpdateCommentDialog.value = false
    }

    fun createComment(content: String) {
        viewModelScope.launch {
            val response = commentRepository.createComment(content, _currentTaskId.value ?: 0)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error creating comment: ${exception.message}")
                }
            }
        }
    }

    fun updateComment(id: Int, content: String) {
        viewModelScope.launch {
            val response = commentRepository.updateComment(id, content)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error updating comment: ${exception.message}")
                }
            }
        }
    }

    fun setSelectedCommentToUpdate(comment: CommentModel?) {
        _selectedCommentToUpdate.value = comment
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            val response = commentRepository.deleteComment(id)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("TaskViewModel", "Error updating comment: ${exception.message}")
                }
            } else {
//                reload comments
                _currentTaskId.value?.let { reloadComments(it) }
            }
        }
    }

    fun reloadComments(taskId: Int = _currentTaskId.value ?: 0) {
        viewModelScope.launch {
            commentRepository.getCommentsByTaskId(taskId).collect { resource ->
                when (resource) {
                    is Resource.Success -> _comments.value = resource.data
                    is Resource.Error, null -> _comments.value = emptyList()
                    else -> {}
                }
            }
        }
    }

    fun setNewCommentValue(content: String) {
        _newComment.value = content
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
    private val tagRepository: TagRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val commentRepository: CommentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(
                taskId = taskId,
                taskRepository = taskRepository,
                tagRepository = tagRepository,
                memberRoleRepository = memberRoleRepository,
                bookmarkRepository = bookmarkRepository,
                commentRepository = commentRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

