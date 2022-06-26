package com.example.githubuserlist.data.network

import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.models.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Request and response models to interact with the server API.
 */
interface GithubUsersApi {

    @GET("/users")
    suspend fun getUsers(@QueryMap queries: Map<String, String>): Response<List<Users>>

    @GET("/users/{username}")
    suspend fun getSingleUser(@Path("username") username: String): Response<SingleUser>

}