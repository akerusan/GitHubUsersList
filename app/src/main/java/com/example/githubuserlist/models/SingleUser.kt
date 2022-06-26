package com.example.githubuserlist.models

import com.google.gson.annotations.SerializedName

/**
 * [SingleUser] data class
 */
data class SingleUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("hireable")
    val hireable: Boolean,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("twitter_username")
    val twitterUsername: String
)
