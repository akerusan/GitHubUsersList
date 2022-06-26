package com.example.githubuserlist.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.githubuserlist.data.Repository
import com.example.githubuserlist.data.database.SingleUserEntity
import com.example.githubuserlist.data.database.UsersEntity
import com.example.githubuserlist.models.Users
import com.example.githubuserlist.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class UsersListViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    /** FROM LOCAL DB */
    val getUsersFromLocal: LiveData<List<UsersEntity>> = repository.local.getUsers().asLiveData()

    private fun insertUsers(usersEntity: UsersEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertUsers(usersEntity)
        }

    /** FROM REMOTE */
    var usersResponse: MutableLiveData<NetworkResult<List<Users>>> = MutableLiveData()

    fun getUsersFromRemote(queries: Map<String, String>) = viewModelScope.launch {
        getUsersSafeCall(queries)
    }

    private suspend fun getUsersSafeCall(queries: Map<String, String>) {
        usersResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getUsers(queries)
                usersResponse.value = handleUsersResponse(response)

                val userList = usersResponse.value!!.data
                if (userList != null) cacheUserListData(userList)

            } catch (e: Exception) {
                usersResponse.value = NetworkResult.Error(message = "Users not found.")
            }
        } else {
            usersResponse.value = NetworkResult.Error(message = "No internet connection.")
        }
    }

    // Check the response from the API
    private fun handleUsersResponse(response: Response<List<Users>>): NetworkResult<List<Users>> {
        return when {
            response.body() == null -> {
                NetworkResult.Error(message = "Users not found.")
            }
            response.code() == 304 -> {
                NetworkResult.Error(message = "Not modified")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body())
            }
            else -> {
                NetworkResult.Error(message = response.message())
            }
        }
    }

    // Store API's results in Database
    private fun cacheUserListData(userList: List<Users>) {
        val usersEntity = UsersEntity(userList)
        insertUsers(usersEntity)
    }

    // Check internet connection capacities
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}