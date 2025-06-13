package com.ucapdm2025.taskspaces.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)
}