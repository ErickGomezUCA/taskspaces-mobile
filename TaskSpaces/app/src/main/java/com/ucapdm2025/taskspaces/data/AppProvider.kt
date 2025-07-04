package com.ucapdm2025.taskspaces.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ucapdm2025.taskspaces.data.database.AppDatabase
import com.ucapdm2025.taskspaces.data.remote.RetrofitInstance
import com.ucapdm2025.taskspaces.data.remote.services.MemberRoleService
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepository
import com.ucapdm2025.taskspaces.data.repository.bookmark.BookmarkRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepository
import com.ucapdm2025.taskspaces.data.repository.comment.CommentRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepository
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.search.SearchRepository
import com.ucapdm2025.taskspaces.data.repository.search.SearchRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.tag.TagRepository
import com.ucapdm2025.taskspaces.data.repository.tag.TagRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl

/**
 * AppProvider is a class that provides instances of repositories and services used in the application.
 * It initializes the database, DAOs, and remote services, and provides methods to access the repositories.
 *
 * @property context The application context used to initialize the database and data store.
 */
private const val AUTH_TOKEN_NAME = "auth_token"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_TOKEN_NAME)

class AppProvider(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)

    //    Auth
    private val authService = RetrofitInstance.authService
    private val authRepository: AuthRepository = AuthRepository(context.dataStore, authService)

//    Media
    private val mediaService = RetrofitInstance.mediaService

    //    Users
    private val userDao = appDatabase.userDao()
    private val userService = RetrofitInstance.userService
    private val userRepository: UserRepository = UserRepositoryImpl(context, userDao, userService, authService, mediaService)

    //    Member roles
    private val memberRoleService = RetrofitInstance.memberRoleService
    private val memberRoleRepository: MemberRoleRepository = MemberRoleRepositoryImpl(memberRoleService)

    //    Workspace
    private val workspaceDao = appDatabase.workspaceDao()
    private val workspaceMemberDao = appDatabase.workspaceMemberDao()
    private val workspaceService = RetrofitInstance.workspaceService
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl(
        authRepository,
        workspaceDao,
        workspaceMemberDao,
        userDao,
        workspaceService,
        userService
    )

    //    Project
    private val projectDao = appDatabase.projectDao()
    private val projectService = RetrofitInstance.projectService
    private val projectRepository: ProjectRepository =
        ProjectRepositoryImpl(projectDao, projectService)

    //    Tag
    private val tagDao = appDatabase.tagDao()
    private val taskTagDao = appDatabase.taskTagDao()
    private val tagService = RetrofitInstance.tagService
    private val tagRepository: TagRepository = TagRepositoryImpl(tagDao, taskTagDao, tagService)

    //    Task
    private val taskDao = appDatabase.taskDao()
    private val taskService = RetrofitInstance.taskService
    private val taskAssignedDao = appDatabase.taskAssignedDao()
    private val taskRepository: TaskRepository = TaskRepositoryImpl(
        taskDao = taskDao, tagDao = tagDao, taskTagDao = taskTagDao, taskService = taskService,
        userDao = userDao,
        taskAssignedDao = taskAssignedDao
    )

//    Search
    private val searchDao = appDatabase.searchDao()
    private val searchService = RetrofitInstance.searchService
    private val searchRepository: SearchRepository = SearchRepositoryImpl(searchDao, searchService)

//    Bookmark
    private val bookmarkDao = appDatabase.bookmarkDao()
    private val bookmarkService = RetrofitInstance.bookmarkService
    private val bookmarkRepository: BookmarkRepository = BookmarkRepositoryImpl(
        authRepository = authRepository,
        bookmarkDao = bookmarkDao,
        bookmarkService = bookmarkService,
        taskDao = taskDao
    )

    //    Comment
    private val commentDao = appDatabase.commentDao()
    private val commentService = RetrofitInstance.commentService
    private val commentRepository: CommentRepository =
        CommentRepositoryImpl(commentDao, userDao, commentService)

    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    fun provideMemberRoleRepository(): MemberRoleRepository {
        return memberRoleRepository
    }

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }

    fun provideWorkspaceRepository(): WorkspaceRepository {
        return workspaceRepository
    }

    fun provideProjectRepository(): ProjectRepository {
        return projectRepository
    }

    fun provideTaskRepository(): TaskRepository {
        return taskRepository
    }

    fun provideSearchRepository(): SearchRepository {
        return searchRepository
    }

    fun provideBookmarkRepository(): BookmarkRepository {
        return bookmarkRepository
    }

    fun provideTagRepository(): TagRepository {
        return tagRepository
    }

    fun provideCommentRepository(): CommentRepository {
        return commentRepository
    }
}

