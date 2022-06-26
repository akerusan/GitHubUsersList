package com.example.githubuserlist.models

import com.google.gson.annotations.SerializedName

/**
 * [Users] data class
 */
data class Users(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
)
