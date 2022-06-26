package com.example.githubuserlist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * [Dao] interface definition of github user list item database.
 */
@Dao
interface UsersDao {

    /**
     * All Users' List
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(usersEntity: UsersEntity)

    @Query("SELECT * FROM users_list_table ORDER BY id")
    fun getUsers(): Flow<List<UsersEntity>>

    /**
     * Single User
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSingleUser(singleUserEntity: SingleUserEntity)

    @Query("SELECT * FROM single_user_info_table WHERE id=:userId")
    fun getSingleUser(userId: Int): Flow<SingleUserEntity>
}