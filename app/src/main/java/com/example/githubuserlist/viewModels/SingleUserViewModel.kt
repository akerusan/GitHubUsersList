package com.example.githubuserlist.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.githubuserlist.data.Repository
import com.example.githubuserlist.data.database.SingleUserEntity
import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class SingleUserViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    /** FROM LOCAL DB */
    fun getSingleUserFromLocal(userId: Int): LiveData<SingleUserEntity> {
        return repository.local.getSingleUser(userId).asLiveData()
    }

    private fun insertSingleUser(singleUserEntity: SingleUserEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertSingleUser(singleUserEntity)
        }

    /** FROM REMOTE */
    var singleUserResponse: MutableLiveData<NetworkResult<SingleUser>> = MutableLiveData()

    fun getSingleUserFromRemote(username: String) = viewModelScope.launch {
        getSingleUserSafeCall(username)
    }

    private suspend fun getSingleUserSafeCall(username: String) {
        singleUserResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getSingleUser(username)
                singleUserResponse.value = handleSingleUserResponse(response)

                val user = singleUserResponse.value!!.data
                if (user != null) cacheSingleUserData(user)

            } catch (e: Exception) {
                singleUserResponse.value = NetworkResult.Error(message = "Users not found.")
            }
        } else {
            singleUserResponse.value = NetworkResult.Error(message = "No internet connection.")
        }
    }

    // Check the response from the API
    private fun handleSingleUserResponse(response: Response<SingleUser>): NetworkResult<SingleUser> {
        return when {
            response.body() == null -> {
                NetworkResult.Error(message = "Users not found.")
            }
            response.code() == 404 -> {
                NetworkResult.Error(message = "Resource not found")
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
    private fun cacheSingleUserData(user: SingleUser) {
        val singleUserEntity = SingleUserEntity(user)
        insertSingleUser(singleUserEntity)
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