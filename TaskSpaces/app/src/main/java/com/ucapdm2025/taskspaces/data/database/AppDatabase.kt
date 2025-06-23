package com.ucapdm2025.taskspaces.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ucapdm2025.taskspaces.data.database.dao.relational.BookmarkDao
import com.ucapdm2025.taskspaces.data.database.dao.ProjectDao
import com.ucapdm2025.taskspaces.data.database.dao.TaskDao
import com.ucapdm2025.taskspaces.data.database.dao.UserDao
import com.ucapdm2025.taskspaces.data.database.dao.WorkspaceDao
import com.ucapdm2025.taskspaces.data.database.dao.catalog.MemberRoleDao
import com.ucapdm2025.taskspaces.data.database.dao.relational.WorkspaceMemberDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.BookmarkEntity
import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity
import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import com.ucapdm2025.taskspaces.data.database.entities.catalog.MemberRoleEntity
import com.ucapdm2025.taskspaces.data.database.entities.relational.WorkspaceMemberEntity

/**
 * AppDatabase is the main database class for the TaskSpaces application.
 */
@Database(
    entities = [UserEntity::class, WorkspaceEntity::class, ProjectEntity::class, TaskEntity::class, BookmarkEntity::class, MemberRoleEntity::class, WorkspaceMemberEntity::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun memberRoleDao(): MemberRoleDao
    abstract fun workspaceMemberDao(): WorkspaceMemberDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = "taskspaces_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also {
                        INSTANCE = it
                    }
                instance
            }
        }
    }
}