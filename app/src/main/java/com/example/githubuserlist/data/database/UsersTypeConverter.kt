package com.example.githubuserlist.data.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.models.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * [TypeConverter] to handle non supported data by the [RoomDatabase]
 * Convert list of User data to a String, back and forth
 * Convert SingleUser data ro a string, back and forth
 */
class UsersTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun userListToString(users: List<Users>): String {
        return gson.toJson(users)
    }

    @TypeConverter
    fun stringToUserList(data: String): List<Users> {
        val listType = object : TypeToken<List<Users>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun singleUserToString(user: SingleUser): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun stringToUser(data: String): SingleUser {
        val userType = object : TypeToken<SingleUser>(){}.type
        return gson.fromJson(data, userType)
    }

}