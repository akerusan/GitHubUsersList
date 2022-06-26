package com.example.githubuserlist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubuserlist.models.Users
import com.example.githubuserlist.utils.Constants.Companion.USERS_TABLE

/**
 * Database [Entity] (table) of a list of all users item.
 */
@Entity(tableName = USERS_TABLE)
class UsersEntity(var usersList: List<Users>) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}