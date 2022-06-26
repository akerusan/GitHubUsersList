package com.example.githubuserlist.data

import com.example.githubuserlist.data.database.SingleUserEntity
import com.example.githubuserlist.data.database.UsersDao
import com.example.githubuserlist.data.database.UsersEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A [LocalDataSource] file to directly query the database
 * The [UsersDao] interface which directly interact with the server API is injected in the constructor via Hilt
 */
class LocalDataSource @Inject constructor(private val usersDao: UsersDao) {

    /**
     * Users' List
     */
    suspend fun insertUsers(usersEntity: UsersEntity) {
        usersDao.insertUsers(usersEntity)
    }

    fun getUsers(): Flow<List<UsersEntity>> {
        return usersDao.getUsers()
    }

    /**
     * Single User
     */
    suspend fun insertSingleUser(singleUserEntity: SingleUserEntity) {
        usersDao.insertSingleUser(singleUserEntity)
    }

    fun getSingleUser(userId: Int): Flow<SingleUserEntity> {
        return usersDao.getSingleUser(userId)
    }
}