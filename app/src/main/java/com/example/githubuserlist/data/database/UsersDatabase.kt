package com.example.githubuserlist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * [RoomDatabase] to provide a github user item list DAO.
 */
@TypeConverters(UsersTypeConverter::class)
@Database(entities = [UsersEntity::class, SingleUserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao
}