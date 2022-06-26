package com.example.githubuserlist.data

import com.example.githubuserlist.data.network.GithubUsersApi
import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.models.Users
import retrofit2.Response
import javax.inject.Inject

/**
 * A [RemoteDataSource] file to directly query the server API
 * The [GithubUsersApi] interface which directly interact with the database is injected in the constructor via Hilt
 */
class RemoteDataSource @Inject constructor(private val githubUsersApi: GithubUsersApi) {

    suspend fun getUsers(queries: Map<String, String>): Response<List<Users>> {
        return githubUsersApi.getUsers(queries)
    }

    suspend fun getSingleUser(username: String): Response<SingleUser> {
        return githubUsersApi.getSingleUser(username)
    }
}