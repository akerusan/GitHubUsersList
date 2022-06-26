package com.example.githubuserlist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.utils.Constants.Companion.SINGLE_USER_TABLE

/**
 * Database [Entity] (table) of a single user item.
 */
@Entity(tableName = SINGLE_USER_TABLE)
class SingleUserEntity(val user: SingleUser) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = user.id
}