package com.ucapdm2025.taskspaces.data.repository.task

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.TagDao
import com.ucapdm2025.taskspaces.data.database.dao.TaskDao
import com.ucapdm2025.taskspaces.data.database.dao.UserDao
import com.ucapdm2025.taskspaces.data.database.dao.relational.TaskAssignedDao
import com.ucapdm2025.taskspaces.data.database.dao.relational.TaskTagDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskAssignedEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskTagEntity
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.TaskRequest
import com.ucapdm2025.taskspaces.data.remote.responses.TaskResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.TaskService
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.utils.toIsoString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okio.IOException
import java.time.LocalDateTime
import kotlin.collections.forEach
import kotlin.collections.map

/**
 * TaskRepositoryImpl is an implementation of the TaskRepository interface.
 * It provides methods to manage tasks in the application, including retrieving,
 * creating, updating, and deleting tasks.
 */
class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val tagDao: TagDao,
    private val userDao: UserDao,
    private val taskTagDao: TaskTagDao,
    private val taskAssignedDao: TaskAssignedDao,
    private val taskService: TaskService,
) : TaskRepository {
    override fun getTasksByProjectId(projectId: Int): Flow<Resource<List<TaskModel>>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch tasks from remote
            val remoteTasks: List<TaskResponse> =
                taskService.getTasksByProjectId(projectId = projectId).content

            //            Save remote tasks to the database
            if (remoteTasks.isNotEmpty()) {
                remoteTasks.forEach {
                    taskDao.createTask(it.toEntity())

//                    Save tags from task response
                    it.tags.forEach { tag ->
                        tagDao.createTag(tag.toEntity())

//                        Assign tag to task
                        taskTagDao.createTaskTag(
                            TaskTagEntity(
                                taskId = it.id,
                                tagId = tag.id
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(
                "TaskRepository: getTasksByProjectId",
                "Error fetching tasks: ${e.message}"
            )
        }

        //        Use local projects
        val localTasks =
            taskDao.getTasksByProjectId(projectId = projectId).map { entities ->
                val tasks = entities.map { entity ->
                    val tags = taskTagDao.getTagsByTaskId(entity.id).first() // <-- Get tags here
                    entity.toDomain(tags = tags.map { it.toDomain() }) // <-- Pass to domain
                     }

                if (tasks.isEmpty()) {
                    //                Logs an error if no projects are found for the user
                    Resource.Error("No tasks found for project with ID: $projectId")
                } else {
                    //                Returns the projects as a success (to domain)
                    Resource.Success(tasks)
                }
            }.distinctUntilChanged()

        emitAll(localTasks)
    }.flowOn(Dispatchers.IO)

    // TODO: Implement this method
//    TODO: Implement this in server backend
    override fun getAssignedTasks(userId: Int): Flow<Resource<List<TaskModel>>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch tasks from remote
            val remoteAssignedTasks: List<TaskResponse> =
                taskService.getAssignedTasksByUserId(userId = userId).content

            //            Save remote tasks to the database
            if (remoteAssignedTasks.isNotEmpty()) {
                remoteAssignedTasks.forEach {
                    taskDao.createTask(it.toEntity())
                }
            }
        } catch (e: Exception) {
            Log.d(
                "TagRepository: getAssignedTasks",
                "Error fetching assigned tasks: ${e.message}"
            )
        }

        //        Use local tasks
        val localAssignedTasks =
            taskAssignedDao.getTasksByUserId(userId = userId).map { entities ->
                val tasks = entities.map { it.toDomain() }

                if (tasks.isEmpty()) {
                    //                Logs an error if no tasks are found for the user
                    Resource.Error("No task found for user with ID: $userId")
                } else {
                    //                Returns the tasks as a success (to domain)
                    Resource.Success(tasks)
                }
            }.distinctUntilChanged()

        emitAll(localAssignedTasks)
    }

    //    TODO: Bug fix > Fetches twice when creating a new task
//    the first with the previous id and then with the new one.
    override fun getTaskById(id: Int): Flow<Resource<TaskModel?>> = flow {
        emit(Resource.Loading)

        Log.d("TaskRepository: getTaskById", "Fetching task with ID: $id")

        try {
            //            Fetch task from remote
            val remoteTask: TaskResponse? =
                taskService.getTaskById(id = id).content

            //            Save remote task to the database
            if (remoteTask != null) {
                taskDao.createTask(remoteTask.toEntity())

                remoteTask.tags.forEach { tag ->
                    tagDao.createTag(tag.toEntity())

//                    Assign tag to task
                    taskTagDao.createTaskTag(
                        TaskTagEntity(
                            taskId = remoteTask.id,
                            tagId = tag.id
                        )
                    )
                }
            } else {
                Log.d("TaskRepository", "No task found with ID: $id")
            }
        } catch (e: Exception) {
            Log.d(
                "TaskRepository: getTaskById",
                "Error fetching task: ${e.message}"
            )
        }

        //        Use local project
        val localTask =
            taskDao.getTaskById(id = id).map { entity ->
                val tags = taskTagDao.getTagsByTaskId(entity?.id ?: 0).first()
                val task = entity?.toDomain(tags = tags.map { it.toDomain() })

                if (task == null) {
                    //                Logs an error if no tasks are found for the user
                    Resource.Error("No task found")
                } else {
                    //                Returns the projects as a success (to domain)
                    Resource.Success(task)

                }
            }.distinctUntilChanged()

        emitAll(localTask)
    }.flowOn(Dispatchers.IO)

    override suspend fun createTask(
        title: String,
        description: String?,
        deadline: LocalDateTime?,
        timer: Float?,
        status: StatusVariations,
        projectId: Int,
    ): Result<TaskModel> {
        val request = TaskRequest(
            title,
            description,
            deadline?.toIsoString(),
            timer,
            status.toString()
        )

        return try {
            val response =
                taskService.createTask(projectId = projectId, request = request)

            val createdTask: TaskModel = response.content.toDomain()

//            Create retrieved project from remote server into the local database
            taskDao.createTask(createdTask.toDatabase())

            Log.d(
                "TaskRepository: createTask",
                "Task created successfully: $createdTask"
            )

            Result.success(createdTask)
        } catch (e: HttpException) {
            Log.e("TaskRepository", "Error creating task: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TaskRepository", "Network error creating task: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TaskRepository", "Unexpected error creating task: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateTask(
        id: Int,
        title: String,
        description: String?,
        deadline: LocalDateTime?,
        timer: Float?,
        status: StatusVariations,
    ): Result<TaskModel> {
        val request = TaskRequest(
            title,
            description,
            deadline?.toIsoString(),
            timer,
            status.toString()
        )

        return try {
            val response =
                taskService.updateTask(id = id, request = request)

            Log.d("TaskRepository: updateTask", deadline?.toIsoString() ?: "")

            val updatedTask: TaskModel = response.content.toDomain()

//            Create retrieved project from remote server into the local database
            taskDao.updateTask(updatedTask.toDatabase())

            Log.d(
                "TaskRepository: updateTask",
                "Task updated successfully: $updatedTask"
            )

            Result.success(updatedTask)
        } catch (e: HttpException) {
            Log.e("TaskRepository", "Error updating task: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TaskRepository", "Network error updating task: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TaskRepository", "Unexpected error updating task: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(id: Int): Result<TaskModel> {
        return try {
            val response = taskService.deleteTask(id = id)

            val deletedTask: TaskModel = response.content.toDomain()

//            Update retrieved project from remote server into the local database
            taskDao.deleteTask(deletedTask.toDatabase())

            Log.d(
                "TaskRepository: deleteTask",
                "Task deleted successfully: $deletedTask"
            )

            Result.success(deletedTask)
        } catch (e: HttpException) {
            Log.e("TaskRepository", "Error deleting task: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TaskRepository", "Network error deleting task: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TaskRepository", "Unexpected error deleting task: ${e.message}")
            Result.failure(e)
        }
    }

    override fun getAssignedMembersByTaskId(taskId: Int): Flow<Resource<List<UserModel>>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch assigned users from remote
            val remoteAssignedMembers: List<UserResponse> =
                taskService.getMembersByTaskId(taskId = taskId).content

            //            Save remote users to the database
            if (remoteAssignedMembers.isNotEmpty()) {
                remoteAssignedMembers.forEach {
                    userDao.createUser(it.toEntity())

//                    Assign user to task
                    taskAssignedDao.createTaskAssigned(
                        TaskAssignedEntity(
                            taskId = taskId,
                            userId = it.id
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.d(
                "TaskRepository: getAssignedMembersByTaskId",
                "Error fetching assigned users: ${e.message}"
            )
        }

        //        Use local members
        val localAssignedMembers =
            taskAssignedDao.getUsersByTaskId(taskId = taskId).map { entities ->
                val users = entities.map { it.toDomain() }

                if (users.isEmpty()) {
                    //                Logs an error if no users are found for the user
                    Resource.Error("No task found for user with ID: $taskId")
                } else {
                    //                Returns the users as a success (to domain)
                    Log.d(
                        "TaskRepository: getAssignedMembersByTaskId",
                        "Assigned members fetched successfully: $users"
                    )
                    Resource.Success(users)
                }
            }.distinctUntilChanged()

        emitAll(localAssignedMembers)
    }


    override suspend fun assignMemberToTask(
        taskId: Int,
        username: String
    ): Result<TaskModel> {
        return try {
//          TODO: Implement this by searching by username instead of user Id
            val userId = 1

            val response =
                taskService.assignMemberToTask(memberId = userId, taskId = taskId)

            val assignedTask: TaskModel = response.content.toDomain()

//            Set retrieved task from remote server into the local database
            taskAssignedDao.createTaskAssigned(
                TaskAssignedEntity(
                    taskId = taskId,
                    userId = userId,
                    createdAt = assignedTask.createdAt
                )
            )

            Log.d(
                "TaskRepository: assignMemberToTask",
                "Member assigned to task successfully: $assignedTask"
            )

            Result.success(assignedTask)
        } catch (e: HttpException) {
            Log.e("TagRepository", "Error assigning member: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TagRepository", "Network error assigning member: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TagRepository", "Unexpected error assigning member: ${e.message}")
            Result.failure(e)
        }

    }

    override suspend fun unassignMemberFromTask(
        taskId: Int,
        userId: Int
    ): Result<TaskModel> {
        return try {
            val response =
                taskService.removeMemberFromTask(memberId = userId, taskId = taskId)

            val unassignedTask: TaskModel = response.content.toDomain()

//            Set retrieved task from remote server into the local database
            taskAssignedDao.deleteTaskAssigned(
                TaskAssignedEntity(
                    taskId = taskId,
                    userId = userId,
                    createdAt = unassignedTask.createdAt
                )
            )

            Log.d(
                "TagRepository: unassignMemberFromTask",
                "Member unassigned to task successfully: $unassignedTask"
            )

            Result.success(unassignedTask)
        } catch (e: HttpException) {
            Log.e("TagRepository", "Error unassigning member: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("TagRepository", "Network error unassigning member: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("TagRepository", "Unexpected error unassigning member: ${e.message}")
            Result.failure(e)
        }
    }
}